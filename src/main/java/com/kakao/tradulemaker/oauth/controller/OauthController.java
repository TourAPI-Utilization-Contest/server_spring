package com.kakao.tradulemaker.oauth.controller;

import com.kakao.tradulemaker.common.Exception.ServiceDefinedException;
import com.kakao.tradulemaker.common.Exception.config.ErrorCode;
import com.kakao.tradulemaker.common.Exception.config.Response;
import com.kakao.tradulemaker.common.Interceptor.Interceptor;
import com.kakao.tradulemaker.member.dto.res.base.MemberBase;
import com.kakao.tradulemaker.member.entity.Member;
import com.kakao.tradulemaker.member.service.MemberService;
import com.kakao.tradulemaker.oauth.dto.req.LoginTest;
import com.kakao.tradulemaker.oauth.dto.res.base.TokenBase;
import com.kakao.tradulemaker.oauth.dto.res.base.TokenTest;
import com.kakao.tradulemaker.oauth.service.KakaoApi;
import com.kakao.tradulemaker.oauth.service.Login;
import lombok.RequiredArgsConstructor;
import org.hibernate.dialect.SybaseSqlAstTranslator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OauthController {

  @Value("${server.admin-token}")
  private String adminToken;

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

  private final MemberService memberService;

  private final Login login;


  @PostMapping("/register")
  public ResponseEntity<Member> registerMember(
          @RequestBody LoginTest memberRegister
          ) {
    Member member = login.registerMember(memberRegister);

    return Response.ok(HttpStatus.CREATED, member);
  }

  @PostMapping("/login-test")
  public ResponseEntity<Long> loginTest(
          @RequestBody LoginTest loginTest
          ) {
    Member member = login.readMemberByEmail(loginTest.getEmail());

    if (!member.getPassword().equals(loginTest.getPassword())) {
      throw new ServiceDefinedException(ErrorCode.NOT_FOUND);
    }

    Long memberId = member.getId();

    return Response.ok(HttpStatus.OK, memberId);
  }

  @GetMapping("/logout-test")
  public ResponseEntity<?> logoutTest() {

    throw new ServiceDefinedException(ErrorCode.EXPIRED_REFRESH_TOKEN);
  }

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
  public ResponseEntity<TokenBase> logIn(@RequestParam String code) {
    TokenBase tokenDto = kakaoApi.getTokens(uriForToken, uriForTokenInfo, code, clientId, clientSecret, redirectUri);

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
  public ResponseEntity<MemberBase> userInfo(
          @RequestAttribute(Interceptor.ACCESS_TOKEN) String accessToken,
          @RequestAttribute(Interceptor.MEMBER_ID) Long memberId
  ) {
    MemberBase memberDto = null;

    if (accessToken.equals(adminToken)) {
      Member admin = memberService.readMemberById(memberId);
      memberDto = new MemberBase(admin.getId(), admin.getEmail(), admin.getNickname(), admin.getProfileUrl());
    } else {
      memberDto = kakaoApi.getMember(uriForUserInfo, accessToken);
    }

    return Response.ok(HttpStatus.OK, memberDto);
  }


  /**
   * 토큰갱신
   *
   * @param refreshToken RefreshToken from Kakao
   * @throws ServiceDefinedException It will be caught by GlobalExceptionHandler
   */
  @GetMapping("/refresh")
  public void refreshTokens(@RequestAttribute(Interceptor.REFRESH_TOKEN) String refreshToken)
          throws ServiceDefinedException {
    TokenBase refreshedTokenDto = kakaoApi.refreshTokens(uriForTokenUpdate, refreshToken, clientId, clientSecret);

    throw new ServiceDefinedException(ErrorCode.SUCCESS_REFRESH, refreshedTokenDto);
  }

  @PutMapping("/user")
  public ResponseEntity<Long> updateMember(
          @RequestAttribute(Interceptor.ACCESS_TOKEN) String accessToken,
          @RequestAttribute(Interceptor.MEMBER_ID) Long memberId,
          @RequestBody String extra
        ) {
        login.updateMember(memberId, extra);

        return Response.ok(HttpStatus.OK, memberId);
    }

}