package com.kakao.tradulemaker.oauth.dto.res;

import lombok.Getter;

@Getter
public class ConnectKakaoDto {

  private final String redirectUri;

  public ConnectKakaoDto(String redirectUri) {
    this.redirectUri = redirectUri;
  }
}
