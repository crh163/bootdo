package com.bootdo.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author rory.chen
 * @date 2021-02-24 17:43
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 注册登录拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //小程序拦截器
        registry.addInterceptor(getLoginHandlerInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/syswxuser/login")
                .excludePathPatterns("/api/index/getIndexInfo");
    }

    @Bean
    public ApiLoginHandlerInterceptor getLoginHandlerInterceptor(){
        return new ApiLoginHandlerInterceptor();
    }

}
