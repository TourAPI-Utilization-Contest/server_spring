package com.kakao.tradulemaker.schedule.controller;

import com.kakao.tradulemaker.common.Exception.config.Response;
import com.kakao.tradulemaker.common.Interceptor.Interceptor;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleController {

  @GetMapping("/{scheduleId}")
  public ResponseEntity<?> findOne(
          @RequestAttribute(Interceptor.USER_ID) String userId
  ) {

    return Response.ok(HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<?> findAll(
          @RequestAttribute(Interceptor.USER_ID) String userId
  ) {

    return Response.ok(HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> create(
          @RequestAttribute(Interceptor.USER_ID) String userId
  ) {

    return Response.ok(HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<?> update(
          @RequestAttribute(Interceptor.USER_ID) String userId
  ) {

    return Response.ok(HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity<?> delete() {

    return Response.ok(HttpStatus.OK);
  }
}
