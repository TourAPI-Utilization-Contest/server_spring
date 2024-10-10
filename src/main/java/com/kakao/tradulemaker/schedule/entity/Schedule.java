package com.kakao.tradulemaker.schedule.entity;

import com.kakao.tradulemaker.member.entity.Member;
import com.kakao.tradulemaker.schedule.dto.req.ScheduleReq;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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

  @Column(name = "ends_at")
  private LocalDate endsAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "icon")
  private String icon;

  @Column(name = "color")
  private Long color;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @OneToMany(mappedBy = "schedule", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<ScheduleDetail> scheduleDetails = new ArrayList<>();

  /**
   * 스케쥴 업데이트
   *
   * @param scheduleReq scheduleReq
   */
  public void update(ScheduleReq scheduleReq) {
    this.title = scheduleReq.getTitle();
    this.startsAt = scheduleReq.getStartsAt();
    this.endsAt = scheduleReq.getEndsAt();
    this.updatedAt = scheduleReq.getUpdatedAt();
    this.icon = scheduleReq.getIcon();
    this.color = scheduleReq.getColor();
  }
}
