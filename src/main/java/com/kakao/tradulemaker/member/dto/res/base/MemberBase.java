package com.kakao.tradulemaker.member.dto.res.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.tradulemaker.common.Exception.ServiceDefinedException;
import com.kakao.tradulemaker.common.Exception.config.ErrorCode;
import lombok.Getter;

@Getter
public class MemberBase {

  private final Long id;

  private final String email;

  private final String nickname;

  private final String profileUrl;

  public MemberBase(
          Long id,
          String email,
          String nickname,
          String profileUrl
  ) {
    this.id = id;
    this.email = email;
    this.nickname = nickname;
    this.profileUrl = profileUrl;
  }

  public MemberBase(String responseBody) {
    ObjectMapper objectMapper = new ObjectMapper();

    try {
      JsonNode jsonNode = objectMapper.readTree(responseBody);
      Long id = jsonNode.get("id").asLong();
      String email = jsonNode.get("kakao_account").get("email").asText();
      String nickname = jsonNode.get("kakao_account").get("profile").get("nickname").asText();
      String profileUrl = jsonNode.get("kakao_account").get("profile").get("profile_image_url").asText();

      this.id = id;
      this.email = email;
      this.nickname = nickname;
      this.profileUrl = profileUrl;

    } catch (Exception e) {
      throw new ServiceDefinedException(ErrorCode.SERVER_ERROR);
    }
  }
}
