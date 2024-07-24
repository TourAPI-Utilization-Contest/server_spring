package com.kakao.tradulemaker.oauth.controller;

import com.kakao.tradulemaker.oauth.dto.res.ConnectKakaoDto;
import com.kakao.tradulemaker.oauth.service.KakaoApi;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
  @Value("${kakao.client-id}")
  String clientId;
  @Value("${kakao.redirect-uri}")
  String redirectUri;

  private final KakaoApi kakaoApi;

  @GetMapping("/connect")
  public String connectKakao() {
    ConnectKakaoDto connectKakaoDto = kakaoApi.getConnectKakaoDto(uriForConnection, clientId, redirectUri);
    
    return "redirect:" + connectKakaoDto.getRedirectUri();
  }

  @GetMapping("/authorize")
  public ResponseEntity<String> getCode(
          @RequestParam String code
  ) {
    ResponseEntity<String> res = kakaoApi.getAccessToken(uriForToken, code, clientId, redirectUri);

    return res;
  }
}