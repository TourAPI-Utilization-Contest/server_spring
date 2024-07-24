package com.kakao.tradulemaker.oauth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OauthController {

  @Value("${kakao.base-uri}")
  String baseUri;
  @Value("${kakao.client-id}")
  String clientId;
  @Value("${kakao.redirect-uri}")
  String redirectUri;


  @GetMapping("/authorize")
  public void connectKakao() {
    StringBuffer url = new StringBuffer();
    url.append(baseUri);
    url.append("client_id=" + clientId);
    url.append("&redirect_uri=" + redirectUri);
    url.append("&response_type=code");

    System.out.println("redirect:" + url.toString());
  }

}