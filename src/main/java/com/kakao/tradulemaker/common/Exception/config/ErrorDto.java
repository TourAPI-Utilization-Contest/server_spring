package com.kakao.tradulemaker.common.Exception.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kakao.tradulemaker.common.Exception.ServiceDefinedException;
import com.kakao.tradulemaker.oauth.dto.res.base.TokenBase;
import lombok.Getter;

@Getter
public class ErrorDto {

  private final int code;

  private final String message;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final TokenBase tokens;

  ErrorDto(ServiceDefinedException e) {
    this.code = e.getCode();
    this.message = e.getMessage();
    this.tokens = e.getTokenDto();
  }
}
