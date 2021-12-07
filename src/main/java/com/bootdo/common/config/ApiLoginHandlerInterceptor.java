package com.bootdo.common.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootdo.api.entity.db.SysWxUser;
import com.bootdo.api.entity.res.common.Response;
import com.bootdo.api.service.SysWxUserService;
import com.bootdo.common.constant.ColumnConsts;
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
    private SysWxUserService sysWxUserService;

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
        log.info("token :【{}】", token);
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        SysWxUser sysWxUser = RedisTemplateUtil.getRedisString(
                CommonConsts.WX_TOKEN_REDIS_PREFIX + token, SysWxUser.class);
        if (sysWxUser == null) {
            sysWxUser = sysWxUserService.getOne(new QueryWrapper<SysWxUser>()
                    .eq(ColumnConsts.TOKEN, token));
            if (sysWxUser == null) {
                sendResponseText(request, response, ResponseCodeEnum.FAIL_NO_LOGIN);
                return false;
            }
            opsForValue.set(CommonConsts.WX_TOKEN_REDIS_PREFIX + token,
                    gson.toJson(sysWxUser), 30, TimeUnit.DAYS);
        }
        // redis做接口幂等
        String idempotentKey = CommonConsts.API_REDIS_IMP_IDEMPOTENT + request.getRequestURI()
                + ":" + sysWxUser.getOpenId();
        if (StringUtils.isNotBlank(RedisTemplateUtil.getRedisString(idempotentKey))) {
            sendResponseText(request, response, ResponseCodeEnum.FAIL_IDEMPOTENT);
            return false;
        }
        // 请求写入redis做幂等 2秒内不允许重复请求 value为当前时间
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
    private void sendResponseText(HttpServletRequest request, HttpServletResponse response,
                                  ResponseCodeEnum codeEnum) throws IOException {
        PrintWriter pw = response.getWriter();
        Response res = new Response();
        res.setCode(codeEnum.getCode());
        res.setMsg(codeEnum.getMsg());
        String resStr = gson.toJson(res);
        log.info("loginHandler request error, url : {}, token : {} ,return {}", request.getRequestURI(),
                request.getHeader(CommonConsts.X_ACCESS_TOKEN), resStr);
        pw.write(resStr);
        pw.flush();
        pw.close();
    }

}
