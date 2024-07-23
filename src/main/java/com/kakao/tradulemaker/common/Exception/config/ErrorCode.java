package com.kakao.tradulemaker.common.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  // 400-BAD_REQUEST [-401, -410]
  BAD_REQUEST_TEST(-401, HttpStatus.BAD_REQUEST, "Bad request test");
  // 403-FORBIDDEN [-411, -420]
  // 404-NOT_FOUND [-421, -430]
  // 500-INTERNAL_SERVER_ERROR

  private final int code;
  private final HttpStatus httpStatus;
  private final String message;

}
