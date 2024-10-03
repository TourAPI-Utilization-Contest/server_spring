package com.kakao.tradulemaker.schedule.entity;

import com.kakao.tradulemaker.schedule.dto.req.ScheduleDetailReq;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity(name = "schedule_detail")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "detail")
    private String detail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public void update(ScheduleDetailReq scheduleDetailReq) {
        this.detail = scheduleDetailReq.getDetail();
    }
}
