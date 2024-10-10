package com.kakao.tradulemaker.common.Interceptor;

import com.kakao.tradulemaker.common.Exception.ServiceDefinedException;
import com.kakao.tradulemaker.oauth.service.KakaoApi;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class Interceptor implements HandlerInterceptor {

  @Value("${server.admin-token}")
  private String adminToken;

  @Value("${kakao.base-uri.token-info}")
  private String uriForTokenInfo;

  @Value("${kakao.base-uri.token-update}")
  private String uriForTokenUpdate;

  private final KakaoApi kakaoApi;

  public static final String ACCESS_TOKEN = "accessToken";

  public static final String REFRESH_TOKEN = "refreshToken";

  public static final String MEMBER_ID = "memberId";

  public Interceptor(KakaoApi kakaoApi) {
    this.kakaoApi = kakaoApi;
  }

  @Override
  public boolean preHandle(
          HttpServletRequest request,
          HttpServletResponse response,
          Object handler
  ) throws IOException {

    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
      return true; // OPTIONS 요청은 그대로 허용
    }

    String accessToken = request.getHeader("access_token");
    String refreshToken = request.getHeader("refresh_token");

    request.setAttribute(ACCESS_TOKEN, accessToken);
    request.setAttribute(REFRESH_TOKEN, refreshToken);

    System.out.println("ACCESS_TOKEN: "+ accessToken);

    if (accessToken.equals(adminToken)) {
      String memberId = request.getHeader("member_id");
      request.setAttribute(MEMBER_ID, memberId);
      return true;
    };

    if (request.getRequestURI().contains("/api/oauth/refresh"))
      return true;

    try {
      Long memberId = kakaoApi.getMemberId(uriForTokenInfo, accessToken);
      request.setAttribute(MEMBER_ID, memberId);
      return true;
    } catch (ServiceDefinedException e) {
      response.sendRedirect("/api/oauth/refresh");
      return false;
    }

  }
}
