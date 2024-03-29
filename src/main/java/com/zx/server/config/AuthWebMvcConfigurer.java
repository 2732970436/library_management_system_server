package com.zx.server.config;

import com.zx.server.filter.AuthHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthWebMvcConfigurer implements WebMvcConfigurer {
  @Autowired
  AuthHandlerInterceptor authHandlerInterceptor;

  /**
   * 给除了 /salary/api/** 的接口都配置拦截器,拦截转向到 authHandlerInterceptor
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authHandlerInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns("/salary/api/**");
  }
}
