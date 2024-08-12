package com.kakao.tradulemaker.oauth.dto.res;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.tradulemaker.common.Exception.ServiceDefinedException;
import com.kakao.tradulemaker.common.Exception.config.ErrorCode;
import lombok.Getter;

@Getter
public class TokenDto {

  private String accessToken;

  private String refreshToken;

  // map String token json into dto format
  public TokenDto(String responseBody) {
    ObjectMapper objectMapper = new ObjectMapper();

    try {
      JsonNode jsonNode = objectMapper.readTree(responseBody);
      String accessToken = jsonNode.get("access_token").asText();
      String refreshToken = jsonNode.get("refresh_token").asText();

      this.accessToken = accessToken;
      this.refreshToken = refreshToken;

    } catch (Exception exception) {
      throw new ServiceDefinedException(ErrorCode.SERVER_ERROR);
    }
  }
}
