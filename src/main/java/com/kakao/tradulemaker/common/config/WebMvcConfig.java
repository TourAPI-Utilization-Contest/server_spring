package com.kakao.tradulemaker.common.config;

import com.kakao.tradulemaker.common.Interceptor.Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  private final Interceptor interceptor;

  public WebMvcConfig(Interceptor interceptor) {
    this.interceptor = interceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(interceptor)
            .excludePathPatterns("/error")
            .excludePathPatterns("/api/oauth/login")
            .excludePathPatterns("/api/oauth/authenticate");
  }
}
