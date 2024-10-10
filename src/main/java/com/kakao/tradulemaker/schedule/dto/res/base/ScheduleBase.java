package com.kakao.tradulemaker.schedule.dto.res.base;

import com.kakao.tradulemaker.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ScheduleBase {

  private final Long id;

  private final String title;

  private final LocalDate startsAt;

  private final LocalDate endsAt;

  private final LocalDateTime updatedAt;

  private final String icon;

  private final Long color;

  public ScheduleBase(Schedule schedule) {
    this.id = schedule.getId();
    this.title = schedule.getTitle();
    this.startsAt = schedule.getStartsAt();
    this.endsAt = schedule.getEndsAt();
    this.updatedAt = schedule.getUpdatedAt();
    this.icon = schedule.getIcon();
    this.color = schedule.getColor();
  }
}
