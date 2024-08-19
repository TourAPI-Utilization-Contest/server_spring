package com.kakao.tradulemaker.schedule.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kakao.tradulemaker.member.dto.res.base.MemberBase;
import com.kakao.tradulemaker.schedule.dto.res.base.ScheduleBase;
import com.kakao.tradulemaker.schedule.entity.Schedule;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ScheduleRes extends ScheduleBase {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final MemberBase member;

  @Builder
  public ScheduleRes(
          Schedule schedule,
          MemberBase memberDto
  ) {
    super(schedule);
    this.member = memberDto;
  }
}
