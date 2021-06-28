package com.bootdo.common.config;

import com.bootdo.api.entity.db.SysWxUser;
import com.bootdo.api.entity.res.Response;
import com.bootdo.common.constant.CommonConsts;
import com.bootdo.common.constant.ResponseCodeEnum;
import com.bootdo.common.utils.RedisTemplateUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author rory.chen
 * @date 2021-02-24 17:44
 */
@Slf4j
@Component
public class ApiLoginHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private Gson gson;

    /**
     * api登录拦截器
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(CommonConsts.X_ACCESS_TOKEN);
        SysWxUser sysWxUser = RedisTemplateUtil.getRedisString(
                CommonConsts.WX_TOKEN_REDIS_PREFIX + token, SysWxUser.class);
        if (sysWxUser == null) {
            sendResponseText(response, ResponseCodeEnum.FAIL_NO_LOGIN);
            return false;
        }
        // redis做接口幂等
        String idempotentKey = CommonConsts.API_REDIS_IMP_IDEMPOTENT + request.getRequestURI()
                + ":" + sysWxUser.getOpenId();
        if (StringUtils.isNotBlank(RedisTemplateUtil.getRedisString(idempotentKey))) {
            sendResponseText(response, ResponseCodeEnum.FAIL_IDEMPOTENT);
            return false;
        }
        // 请求写入redis做幂等 2秒内不允许重复请求 value为当前时间
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        opsForValue.set(idempotentKey, LocalDateTime.now().toString(), 2, TimeUnit.SECONDS);
        request.setAttribute(CommonConsts.WX_API_USER_INFO, sysWxUser);
        return true;
    }

    /**
     * 发送响应结果
     *
     * @param response
     * @param codeEnum
     * @throws IOException
     */
    private void sendResponseText(HttpServletResponse response, ResponseCodeEnum codeEnum) throws IOException {
        PrintWriter pw = response.getWriter();
        Response res = new Response();
        res.setCode(codeEnum.getCode());
        res.setMsg(codeEnum.getMsg());
        pw.write(gson.toJson(res));
        pw.flush();
        pw.close();
    }

}
