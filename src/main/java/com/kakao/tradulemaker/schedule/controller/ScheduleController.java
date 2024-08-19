package com.kakao.tradulemaker.schedule.controller;

import com.kakao.tradulemaker.common.Exception.config.Response;
import com.kakao.tradulemaker.common.Interceptor.Interceptor;
import com.kakao.tradulemaker.member.dto.res.MemberRes;
import com.kakao.tradulemaker.member.dto.res.base.MemberDto;
import com.kakao.tradulemaker.member.entity.Member;
import com.kakao.tradulemaker.member.service.MemberService;
import com.kakao.tradulemaker.oauth.service.KakaoApi;
import com.kakao.tradulemaker.schedule.dto.res.ScheduleRes;
import com.kakao.tradulemaker.schedule.dto.res.base.ScheduleDto;
import com.kakao.tradulemaker.schedule.entity.Schedule;
import com.kakao.tradulemaker.schedule.service.ScheduleService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleController {

  @Value("${kakao.base-uri.user-info}")
  private String uriForUserInfo;

  private final KakaoApi kakaoApi;

  private final ScheduleService scheduleService;

  private final MemberService memberService;

  @GetMapping("/{scheduleId}")
  public ResponseEntity<ScheduleRes> findOne(
          @PathVariable("scheduleId") Long scheduleId,
          @RequestAttribute(Interceptor.MEMBER_ID) Long memberId,
          @RequestAttribute(Interceptor.ACCESS_TOKEN) String accessToken,
          @RequestParam(required = true, value = "contains-user") Boolean containsUser
  ) {
    Schedule schedule = scheduleService.readSchedule(scheduleId, memberId);

    MemberDto member = null;
    if (containsUser)
      member = kakaoApi.getMember(uriForUserInfo, accessToken);

    ScheduleRes scheduleRes = ScheduleRes.builder()
            .schedule(schedule)
            .memberDto(member)
            .build();

    return Response.ok(HttpStatus.OK, scheduleRes);
  }

  @GetMapping
  public ResponseEntity<MemberRes> findAll(
          @RequestAttribute(Interceptor.MEMBER_ID) Long memberId,
          @RequestAttribute(Interceptor.ACCESS_TOKEN) String accessToken
  ) {
    Member member = memberService.readMemberById(memberId);
    List<ScheduleDto> scheduleDtoList = member.getSchedules()
            .stream()
            .map(ScheduleDto::new)
            .toList();

    String responseBody = kakaoApi.fetchGet(uriForUserInfo, accessToken).toString();

    MemberRes memberRes = MemberRes.builder()
            .responseBody(responseBody)
            .scheduleDtoList(scheduleDtoList)
            .build();

    return Response.ok(HttpStatus.OK, memberRes);
  }

  @PostMapping
  public ResponseEntity<?> create(
          @RequestAttribute(Interceptor.MEMBER_ID) Long memberId
  ) {

    return Response.ok(HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<?> update(
          @RequestAttribute(Interceptor.MEMBER_ID) Long memberId
  ) {

    return Response.ok(HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity<?> delete() {

    return Response.ok(HttpStatus.OK);
  }
}
