package com.kakao.tradulemaker.common.Exception;

import com.kakao.tradulemaker.common.Exception.config.ErrorDto;
import com.kakao.tradulemaker.common.Exception.config.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = ServiceDefinedException.class)
  public ResponseEntity<ErrorDto> returnServiceException(ServiceDefinedException e) {
    return Response.fail(e);
  }
}
