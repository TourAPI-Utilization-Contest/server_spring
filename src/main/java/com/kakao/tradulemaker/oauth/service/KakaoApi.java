package com.kakao.tradulemaker.oauth.service;

import com.kakao.tradulemaker.oauth.dto.res.ConnectKakaoDto;
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

  public ConnectKakaoDto getConnectKakaoDto(
          String baseUri,
          String clientId,
          String redirectUri
  ) {
    StringBuffer url =
            new StringBuffer()
                    .append(baseUri)
                    .append("client_id=" + clientId)
                    .append("&redirect_uri=" + redirectUri)
                    .append("&response_type=code");

    String agreementUri = url.toString();

    System.out.println(agreementUri);

    return new ConnectKakaoDto(agreementUri);
  }

  public ResponseEntity<String> getAccessToken(
          String baseUri,
          String code,
          String clientId,
          String redirectUri
  ) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");


    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "authorization_code");
    body.add("client_id", clientId);
    body.add("redirect_uri", redirectUri);
    body.add("code", code);

    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

    return getResponse(baseUri, HttpMethod.POST, entity);
  }

  private ResponseEntity<String> getResponse(
          String baseUri,
          HttpMethod method,
          HttpEntity<MultiValueMap<String, String>> entity
  ) {

    ResponseEntity<String> res = new RestTemplate().exchange(
            baseUri,
            method,
            entity,
            String.class
    );

    return res;
  }
}
