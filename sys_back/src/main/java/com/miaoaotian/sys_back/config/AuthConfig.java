package com.miaoaotian.sys_back.config;

import com.miaoaotian.sys_back.utils.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/captchaImage","/register","/sendEmail", "/avatar/**","/user/**","/**/*.js","/**/*.css","/**/*.png");
//                .excludePathPatterns("/**");

    }
}