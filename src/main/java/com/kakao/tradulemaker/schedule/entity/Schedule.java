package com.kakao.tradulemaker.schedule.entity;

import com.kakao.tradulemaker.member.entity.Member;
import com.kakao.tradulemaker.schedule.dto.req.ScheduleReq;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Builder
@Entity(name = "schedule")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "starts_at")
  private LocalDate startsAt;

  @Column(name = "created_at")
  private LocalDate createdAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  public void update(ScheduleReq scheduleReq) {
    this.title = scheduleReq.getTitle();
    this.startsAt = scheduleReq.getStartsAt();
    this.createdAt = scheduleReq.getCreatedAt();
  }
}
