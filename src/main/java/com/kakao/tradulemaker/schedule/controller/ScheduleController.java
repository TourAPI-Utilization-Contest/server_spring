package com.kakao.tradulemaker.schedule.controller;

import com.kakao.tradulemaker.common.Exception.config.Response;
import com.kakao.tradulemaker.common.Interceptor.Interceptor;
import com.kakao.tradulemaker.member.dto.res.MemberRes;
import com.kakao.tradulemaker.member.dto.res.base.MemberBase;
import com.kakao.tradulemaker.member.entity.Member;
import com.kakao.tradulemaker.member.service.MemberService;
import com.kakao.tradulemaker.oauth.service.KakaoApi;
import com.kakao.tradulemaker.schedule.dto.req.ScheduleReq;
import com.kakao.tradulemaker.schedule.dto.res.ScheduleRes;
import com.kakao.tradulemaker.schedule.dto.res.base.ScheduleBase;
import com.kakao.tradulemaker.schedule.entity.Schedule;
import com.kakao.tradulemaker.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    MemberBase member = null;
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
    List<ScheduleBase> scheduleDtoList = member.getSchedules()
            .stream()
            .map(ScheduleBase::new)
            .toList();

    String responseBody = kakaoApi.fetchGet(uriForUserInfo, accessToken).toString();

    MemberRes memberRes = MemberRes.builder()
            .responseBody(responseBody)
            .scheduleDtoList(scheduleDtoList)
            .build();

    return Response.ok(HttpStatus.OK, memberRes);
  }

  @PostMapping
  public ResponseEntity<Long> create(
          @RequestAttribute(Interceptor.MEMBER_ID) Long memberId,
          @RequestBody ScheduleReq scheduleReq
          ) {
    Member member = memberService.readMemberById(memberId);

    Long scheduleId = scheduleService.create(scheduleReq, member);

    return Response.ok(HttpStatus.CREATED, scheduleId);
  }

  @PutMapping("/{scheduleId}")
  public ResponseEntity<Long> update(
          @PathVariable("scheduleId") Long scheduleId,
          @RequestAttribute(Interceptor.MEMBER_ID) Long memberId,
          @RequestBody ScheduleReq scheduleReq
  ) {
    Schedule schedule = scheduleService.readSchedule(scheduleId, memberId);
    Long id = scheduleService.update(schedule, scheduleReq);

    return Response.ok(HttpStatus.OK, id);
  }

  @DeleteMapping("/{scheduleId}")
  public ResponseEntity<?> delete(
          @RequestAttribute(Interceptor.MEMBER_ID) Long memberId,
          @PathVariable("scheduleId") Long scheduleId
  ) {
    Schedule schedule = scheduleService.readSchedule(scheduleId, memberId);
    Long id = scheduleService.delete(schedule);


    return Response.ok(HttpStatus.OK, id);
  }
}
