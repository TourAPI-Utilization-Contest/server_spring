package com.kakao.tradulemaker.oauth.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.tradulemaker.common.Exception.ServiceDefinedException;
import com.kakao.tradulemaker.common.Exception.config.ErrorCode;
import com.kakao.tradulemaker.member.dto.res.base.MemberDto;
import com.kakao.tradulemaker.member.entity.Member;
import com.kakao.tradulemaker.member.repository.MemberRepository;
import com.kakao.tradulemaker.oauth.dto.res.base.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class KakaoApi {

  @Value("${kakao.base-uri.token-update}")
  String uriForRefreshingToken;

  private final MemberRepository memberRepository;

  // 카카오 로그인 페이지 리다이렉션 URI
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

  // 사용자 로그인/회원가입 처리 후 토큰정보 발급
  public TokenDto getTokens(
          String uriForToken,
          String uriForTokenInfo,
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

    String responseBody = fetchPost(uriForToken, entity);
    TokenDto tokenDto = new TokenDto(responseBody);

    // Check if this user has already been registered on this service.
    Long userId = getUserId(uriForTokenInfo, tokenDto.getAccessToken());
    if (!memberRepository.existsById(userId))
      memberRepository.save(new Member(userId));

    return tokenDto;
  }

  // 유저정보 단일 조회
  public MemberDto getMember(
          String baseUri,
          String accessToken
  ) {
    String responseBody = String.valueOf(fetchGet(baseUri, accessToken));

    return new MemberDto(responseBody);
  }

  // 로그아웃
  public void disconnect(
          String baseUri,
          String accessToken
  ) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer" + " " + accessToken);
    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(null, headers);

    fetchPost(baseUri, entity);
  }

  // 토큰 갱신
  public TokenDto refreshTokens(
          String baseUri,
          String refreshToken,
          String clientId,
          String clientSecret
  ) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "refresh_token");
    body.add("client_id", clientId);
    body.add("client_secret", clientSecret);
    body.add("refresh_token", refreshToken);

    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
    String responseBody = fetchPost(baseUri, entity);

    return new TokenDto(responseBody);
  }

  // 토큰 정보 확인 (인가)
  public Long getUserId(
          String uriForTokenInfo,
          String accessToken
  ) {
    String responseBody = String.valueOf(fetchGet(uriForTokenInfo, accessToken));
    ObjectMapper objectMapper = new ObjectMapper();
    Long userId = null;

    try {
      JsonNode jsonNode = objectMapper.readTree(responseBody);
      userId = jsonNode.get("id").asLong();
    } catch (Exception e) {
      throw new ServiceDefinedException(ErrorCode.SERVER_ERROR);
    }

    return userId;
  }

  // GET request
  private StringBuilder fetchGet(
          String baseUri,
          String accessToken
  ) {
    StringBuilder stringBuffer = null;

    try {
      URL url = new URL(baseUri);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Authorization", "Bearer" + " " + accessToken);
      connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
      connection.setDoOutput(true);

      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      stringBuffer = new StringBuilder();
      String inputLine;

      while ((inputLine = bufferedReader.readLine()) != null) {
        stringBuffer.append(inputLine);
      }

      bufferedReader.close();
      return stringBuffer;
    } catch (IOException e) {
      throw new ServiceDefinedException(ErrorCode.INVALID_ACCESS_TOKEN);
    }
  }

  // POST request
  private String fetchPost(
          String baseUri,
          HttpEntity<MultiValueMap<String, String>> entity
  ) {
    String responseBody = null;
    try {
      responseBody = new RestTemplate().exchange(
              baseUri,
              HttpMethod.POST,
              entity,
              String.class
      ).getBody();
    } catch (HttpClientErrorException e) {
      throw new ServiceDefinedException(ErrorCode.INVALID_ACCESS_TOKEN);
    }
    return responseBody;
  }
}
