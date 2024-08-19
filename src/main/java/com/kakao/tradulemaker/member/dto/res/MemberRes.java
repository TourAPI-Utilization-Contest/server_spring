package com.kakao.tradulemaker.member.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kakao.tradulemaker.member.dto.res.base.MemberDto;
import com.kakao.tradulemaker.schedule.dto.res.base.ScheduleDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberRes extends MemberDto {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final List<ScheduleDto> scheduleList;

  @Builder
  public MemberRes(
          String responseBody,
          List<ScheduleDto> scheduleDtoList
  ) {
    super(responseBody);
    this.scheduleList = scheduleDtoList;
  }
}
