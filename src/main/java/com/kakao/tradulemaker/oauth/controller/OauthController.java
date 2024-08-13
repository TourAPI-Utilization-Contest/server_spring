package com.kakao.tradulemaker.oauth.controller;

import com.kakao.tradulemaker.common.Exception.ServiceDefinedException;
import com.kakao.tradulemaker.common.Exception.config.ErrorCode;
import com.kakao.tradulemaker.common.Exception.config.Response;
import com.kakao.tradulemaker.common.Interceptor.Interceptor;
import com.kakao.tradulemaker.member.dto.res.base.MemberDto;
import com.kakao.tradulemaker.oauth.dto.res.base.TokenDto;
import com.kakao.tradulemaker.oauth.service.KakaoApi;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OauthController {

  @Value("${server.host}")
  private String host;

  @Value("${server.port}")
  private String port;

  @Value("${kakao.base-uri.connect}")
  private String uriForConnection;

  @Value("${kakao.base-uri.get-token}")
  private String uriForToken;

  @Value("${kakao.base-uri.token-info}")
  private String uriForTokenInfo;

  @Value("${kakao.base-uri.token-update}")
  private String uriForTokenUpdate;

  @Value("${kakao.base-uri.user-info}")
  private String uriForUserInfo;

  @Value("${kakao.base-uri.log-out}")
  private String uriForLogout;

  @Value("${kakao.redirect-uri}")
  private String redirectUri;

  @Value("${kakao.client.id}")
  private String clientId;

  @Value("${kakao.client.secret}")
  private String clientSecret;

  private final KakaoApi kakaoApi;

  /**
   * 인가코드 받기 & 리디렉션
   *
   * @return Redirection Url to api/oauth/authenticate
   */
  @GetMapping("/login")
  public String connectOauth() {
    String connectionUri = kakaoApi.getConnectionUri(uriForConnection, clientId, redirectUri);

    return "redirect:" + connectionUri;
  }

  /**
   * 토큰 받기 (로그인/회원가입)
   * Server Only Use
   *
   * @param code Authentication Code from Kakao
   * @return ResponseEntity<TokenDto>
   */
  @GetMapping("/authenticate")
  public ResponseEntity<TokenDto> logIn(@RequestParam String code) {
    TokenDto tokenDto = kakaoApi.getTokens(uriForToken, uriForTokenInfo, code, clientId, clientSecret, redirectUri);

    return Response.ok(HttpStatus.OK, tokenDto);
  }

  /**
   * 로그아웃
   *
   * @param accessToken AccessToken from Kakao
   * @return ResponseEntity<?>
   */
  @PostMapping("/logout")
  public ResponseEntity<?> logout(@RequestAttribute(Interceptor.ACCESS_TOKEN) String accessToken) {
    kakaoApi.disconnect(uriForLogout, accessToken);

    return Response.ok(HttpStatus.OK);
  }

  /**
   * 유저 정보 받기
   *
   * @param accessToken AccessToken from Kakao
   * @return ResponseEntity<MemberDto>
   */
  @GetMapping("/user")
  public ResponseEntity<MemberDto> userInfo(@RequestAttribute(Interceptor.ACCESS_TOKEN) String accessToken) {
    MemberDto memberDto = kakaoApi.getMember(uriForUserInfo, accessToken);

    return Response.ok(HttpStatus.OK, memberDto);
  }


  /**
   * 토큰갱신
   *
   * @param refreshToken RefreshToken from Kakao
   * @throws ServiceDefinedException It will be caught by GlobalExceptionHandler
   */
  @GetMapping("/refresh")
  public void refreshTokens(
          @RequestAttribute(Interceptor.REFRESH_TOKEN) String refreshToken
  ) throws ServiceDefinedException {
    TokenDto refreshedTokenDto = kakaoApi.refreshTokens(uriForTokenUpdate, refreshToken, clientId, clientSecret);

    throw new ServiceDefinedException(ErrorCode.SUCCESS_REFRESH, refreshedTokenDto);
  }
}