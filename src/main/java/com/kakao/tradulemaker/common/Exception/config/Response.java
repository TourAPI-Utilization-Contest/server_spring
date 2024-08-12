package com.kakao.tradulemaker.common.Exception.config;

import com.kakao.tradulemaker.common.Exception.ServiceDefinedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response<T> {

  /**
   * Simple OK sign. (Only HttpStatus)
   *
   * @param httpStatus HttpStatus
   * @return ResponseEntity
   */
  public static ResponseEntity<?> ok(HttpStatus httpStatus) {
    return new ResponseEntity<>(httpStatus);
  }

  /**
   * Ok Sign with response body included.
   *
   * @param httpStatus HttpStatus
   * @param body       T
   * @return ResponseEntity<T>
   */
  public static <T> ResponseEntity<T> ok(
          HttpStatus httpStatus,
          T body
  ) {
    return ResponseEntity
            .status(httpStatus)
            .body(body);
  }

  /**
   * Defined error response with custom dto included.
   * (Only Executed in the Global Exception.)
   *
   * @param e ErrorCode
   * @return ResponseEntity<ErrorDto>
   */
  public static ResponseEntity<ErrorDto> fail(ServiceDefinedException e) {
    return ResponseEntity
            .status(e.getHttpStatus())
            .body(new ErrorDto(e));
  }
}
