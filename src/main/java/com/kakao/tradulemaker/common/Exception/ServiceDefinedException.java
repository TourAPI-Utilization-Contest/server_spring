package com.kakao.tradulemaker.common.Exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kakao.tradulemaker.common.Exception.config.ErrorCode;
import com.kakao.tradulemaker.oauth.dto.res.base.TokenBase;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceDefinedException extends RuntimeException {

  private final HttpStatus httpStatus;

  private final int code;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final TokenBase tokenDto;

  /**
   * 기본 에러양식
   *
   * @param e ErrorCode
   */
  public ServiceDefinedException(
          ErrorCode e
  ) {
    super(e.getMessage());
    this.httpStatus = e.getHttpStatus();
    this.code = e.getCode();
    this.tokenDto = null;
  }

  /**
   * 토큰정보를 담은 에러양식
   *
   * @param e        ErrorCode
   * @param tokenDto 리프레쉬 된 토큰 (AccessToken, RefreshToken[Optional])
   */
  public ServiceDefinedException(
          ErrorCode e,
          TokenBase tokenDto
  ) {
    super(e.getMessage());
    this.httpStatus = e.getHttpStatus();
    this.code = e.getCode();
    this.tokenDto = tokenDto;
  }
}
