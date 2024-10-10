package com.kakao.tradulemaker.schedule.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleReq {

  private String title;

  private LocalDate startsAt;

  private LocalDate endsAt;

  private LocalDateTime updatedAt;

  private String icon;

  private Long color;
}
