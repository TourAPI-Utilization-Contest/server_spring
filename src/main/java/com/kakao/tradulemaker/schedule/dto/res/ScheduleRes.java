package com.kakao.tradulemaker.schedule.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kakao.tradulemaker.member.dto.res.base.MemberBase;
import com.kakao.tradulemaker.schedule.dto.res.base.ScheduleBase;
import com.kakao.tradulemaker.schedule.dto.res.base.ScheduleDetailBase;
import com.kakao.tradulemaker.schedule.entity.Schedule;
import com.kakao.tradulemaker.schedule.entity.ScheduleDetail;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ScheduleRes extends ScheduleBase {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final MemberBase member;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final List<ScheduleDetailBase> details;

  @Builder
  public ScheduleRes(
          Schedule schedule,
          MemberBase memberDto,
          List<ScheduleDetailBase> details
  ) {
    super(schedule);
    this.member = memberDto;
    this.details = details;
  }
}
