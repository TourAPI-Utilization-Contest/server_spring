package com.kakao.tradulemaker.schedule.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleReq {

  private String title;

  private LocalDate startsAt;

  private LocalDate createdAt;
}
