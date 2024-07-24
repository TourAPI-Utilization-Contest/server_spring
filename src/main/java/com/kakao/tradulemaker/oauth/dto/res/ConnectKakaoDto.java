package com.kakao.tradulemaker.oauth.dto.res;

import lombok.Getter;

@Getter
public class TestDto {

  private final String redirectUri;

  public TestDto(String redirectUri) {
    this.redirectUri = redirectUri;
  }
}
