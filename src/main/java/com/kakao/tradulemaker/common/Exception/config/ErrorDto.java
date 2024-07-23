package com.kakao.tradulemaker.common.Exception.config;

import com.kakao.tradulemaker.common.Exception.ServiceDefinedException;
import lombok.Getter;

@Getter
public class ErrorDto {
  private final int code;
  private final String message;

  ErrorDto(ServiceDefinedException e) {
    this.code = e.getCode();
    this.message = e.getMessage();
  }
}
