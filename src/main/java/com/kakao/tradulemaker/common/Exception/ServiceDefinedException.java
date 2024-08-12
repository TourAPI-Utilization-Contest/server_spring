package com.kakao.tradulemaker.common.Exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kakao.tradulemaker.common.Exception.config.ErrorCode;
import com.kakao.tradulemaker.oauth.dto.res.base.TokenDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceDefinedException extends RuntimeException {

  private final HttpStatus httpStatus;

  private final int code;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final TokenDto tokenDto;

  public ServiceDefinedException(
          ErrorCode e
  ) {
    super(e.getMessage());
    this.httpStatus = e.getHttpStatus();
    this.code = e.getCode();
    this.tokenDto = null;
  }

  public ServiceDefinedException(
          ErrorCode e,
          TokenDto tokenDto
  ) {
    super(e.getMessage());
    this.httpStatus = e.getHttpStatus();
    this.code = e.getCode();
    this.tokenDto = tokenDto;
  }
}
