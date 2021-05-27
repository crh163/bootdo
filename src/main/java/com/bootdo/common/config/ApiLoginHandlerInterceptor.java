package com.bootdo.common.config;

import com.bootdo.api.entity.db.SysWxUser;
import com.bootdo.common.constant.CommonConsts;
import com.bootdo.common.utils.RedisTemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author rory.chen
 * @date 2021-02-24 17:44
 */
@Slf4j
public class ApiLoginHandlerInterceptor implements HandlerInterceptor {

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
        SysWxUser sysWxUser = RedisTemplateUtil.getRedisString(token, SysWxUser.class);
        if (sysWxUser != null) {
            request.setAttribute(CommonConsts.WX_API_USER_INFO, sysWxUser);
            return true;
        } else {
            return false;
        }
    }

}
