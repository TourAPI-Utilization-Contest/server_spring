package com.kakao.tradulemaker.definition.Exception;

import com.kakao.tradulemaker.definition.Exception.config.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceDefinedException extends RuntimeException {

  private final HttpStatus httpStatus;

  private final int code;

  public ServiceDefinedException(ErrorCode e) {
    super(e.getMessage());
    this.httpStatus = e.getHttpStatus();
    this.code = e.getCode();
  }
}
