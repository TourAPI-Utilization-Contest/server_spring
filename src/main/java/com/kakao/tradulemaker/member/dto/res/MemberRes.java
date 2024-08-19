package com.kakao.tradulemaker.member.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kakao.tradulemaker.member.dto.res.base.MemberBase;
import com.kakao.tradulemaker.schedule.dto.res.base.ScheduleBase;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberRes extends MemberBase {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final List<ScheduleBase> scheduleList;

  @Builder
  public MemberRes(
          String responseBody,
          List<ScheduleBase> scheduleDtoList
  ) {
    super(responseBody);
    this.scheduleList = scheduleDtoList;
  }
}
