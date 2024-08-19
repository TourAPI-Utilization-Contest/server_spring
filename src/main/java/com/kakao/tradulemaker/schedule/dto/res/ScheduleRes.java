package com.kakao.tradulemaker.schedule.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kakao.tradulemaker.member.dto.res.base.MemberDto;
import com.kakao.tradulemaker.schedule.dto.res.base.ScheduleDto;
import com.kakao.tradulemaker.schedule.entity.Schedule;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ScheduleRes extends ScheduleDto {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final MemberDto member;

  @Builder
  public ScheduleRes(
          Schedule schedule,
          MemberDto memberDto
  ) {
    super(schedule);
    this.member = memberDto;
  }
}
