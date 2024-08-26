package com.kakao.tradulemaker.oauth.dto.res.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.tradulemaker.common.Exception.ServiceDefinedException;
import com.kakao.tradulemaker.common.Exception.config.ErrorCode;
import lombok.Getter;

@Getter
public class TokenBase {

  private final String accessToken;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final String refreshToken;

  // map String token json into dto format
  public TokenBase(String responseBody) {
    ObjectMapper objectMapper = new ObjectMapper();

    try {
      JsonNode jsonNode = objectMapper.readTree(responseBody);
      this.accessToken = jsonNode.get("access_token").asText();
      JsonNode refT = jsonNode.get("refresh_token");
      this.refreshToken = refT == null ? null : refT.asText();

    } catch (Exception exception) {
      throw new ServiceDefinedException(ErrorCode.SERVER_ERROR);
    }
  }
}
