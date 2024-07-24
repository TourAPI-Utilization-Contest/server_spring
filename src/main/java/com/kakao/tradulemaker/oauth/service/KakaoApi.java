package com.kakao.tradulemaker.oauth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KakaoApi {

  public String getConnectionUri(
          String baseUri,
          String clientId,
          String redirectUri
  ) {

    return baseUri +
            "client_id=" + clientId +
            "&redirect_uri=" + redirectUri +
            "&response_type=code";
  }

  public ResponseEntity<String> getTokens(
          String baseUri,
          String code,
          String clientId,
          String clientSecret,
          String redirectUri
  ) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "authorization_code");
    body.add("client_id", clientId);
    body.add("client_secret", clientSecret);
    body.add("redirect_uri", redirectUri);
    body.add("code", code);

    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

    return fetchPost(baseUri, entity);
  }

  private ResponseEntity<String> fetchPost(
          String baseUri,
          HttpEntity<MultiValueMap<String, String>> entity
  ) {

    return new RestTemplate().exchange(
            baseUri,
            HttpMethod.POST,
            entity,
            String.class
    );
  }
}
