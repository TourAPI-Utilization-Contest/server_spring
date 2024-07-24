package com.kakao.tradulemaker.definition.Exception.config;

import com.kakao.tradulemaker.definition.Exception.ServiceDefinedException;
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
