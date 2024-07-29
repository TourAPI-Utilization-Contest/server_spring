package com.kakao.tradulemaker.oauth.dto.res;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenDto {

  private String accessToken = null;
  
  private String refreshToken = null;

  public TokenDto(String responseBody) {
    ObjectMapper objectMapper = new ObjectMapper();

    try {
      JsonNode jsonNode = objectMapper.readTree(responseBody);
      String accessToken = jsonNode.get("access_token").asText();
      String refreshToken = jsonNode.get("refresh_token").asText();

      this.accessToken = accessToken;
      this.refreshToken = refreshToken;

    } catch (JsonProcessingException exception) {
//      Missing statements.
    }
  }
}
