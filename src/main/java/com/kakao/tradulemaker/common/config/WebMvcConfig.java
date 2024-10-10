package com.kakao.tradulemaker.common.config;

import com.kakao.tradulemaker.common.Interceptor.Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
            .excludePathPatterns("/api/oauth/authenticate")
            .excludePathPatterns("/api/oauth/login-test")
            .excludePathPatterns("/api/oauth/logout-test")
            .excludePathPatterns("/api/oauth/register");
  }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
      registry.addMapping("/**") // 모든 경로에 대해 CORS 허용
              .allowedOrigins("*") // 모든 Origin 허용
              .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
              .allowedHeaders("access_token", "member_id", "refresh_token", "*") // 허용할 헤더
              .allowCredentials(false) // 쿠키는 비허용 (모든 오리진 허용 시 true는 사용할 수 없음)
              .maxAge(3600); // CORS 허용 정보 캐시 시간 (초)
    }
}
