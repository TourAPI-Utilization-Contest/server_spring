package com.kakao.tradulemaker.common.config;

import com.kakao.tradulemaker.common.Exception.ServiceDefinedException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorDto {
  private final HttpStatus httpStatus;
  private final int code;
  private final String message;

  ErrorDto(ServiceDefinedException e) {
    this.httpStatus = e.getHttpStatus();
    this.code = e.getCode();
    this.message = e.getMessage();
  }
}
