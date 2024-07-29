package com.kakao.tradulemaker.oauth.controller;

import com.kakao.tradulemaker.common.Exception.config.Response;
import com.kakao.tradulemaker.oauth.dto.res.TokenDto;
import com.kakao.tradulemaker.oauth.service.KakaoApi;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OauthController {

  @Value("${kakao.base-uri.connect}")
  String uriForConnection;

  @Value("${kakao.base-uri.get-token}")
  String uriForToken;

  @Value("${kakao.redirect-uri}")
  String redirectUri;

  @Value("${kakao.client.id}")
  String clientId;

  @Value("${kakao.client.secret}")
  String clientSecret;

  private final KakaoApi kakaoApi;

  @GetMapping("/connect")
  public String connectOauth() {
    String connectionUri = kakaoApi.getConnectionUri(uriForConnection, clientId, redirectUri);

    return "redirect:" + connectionUri;
  }

  @GetMapping("/authorize")
  public ResponseEntity<TokenDto> logIn(@RequestParam String code) {
    TokenDto tokenDto = kakaoApi.getTokens(uriForToken, code, clientId, clientSecret, redirectUri);

    return Response.ok(HttpStatus.OK, tokenDto);
  }
}