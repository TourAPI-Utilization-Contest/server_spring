package com.kakao.tradulemaker.common.Exception.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  // 400-BAD_REQUEST [-401, -410]
  INVALID_ACCESS_TOKEN(-401, HttpStatus.BAD_REQUEST, "Invalid Access token."),
  // 403-FORBIDDEN [-411, -420]
  EXPIRED_REFRESH_TOKEN(-411, HttpStatus.FORBIDDEN, "Refresh token has been expired."),
  // 404-NOT_FOUND [-421, -430]
  // 500-INTERNAL_SERVER_ERROR
  SERVER_ERROR(-500, HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong."),
  // SUCCESS_REFRESH
  SUCCESS_REFRESH(0, HttpStatus.CREATED, "Token has been refreshed. Please check newly updated tokens.");


  private final int code;
  private final HttpStatus httpStatus;
  private final String message;
}
