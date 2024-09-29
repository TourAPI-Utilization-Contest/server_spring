package com.kakao.tradulemaker.schedule.dto.res.base;

import com.kakao.tradulemaker.schedule.entity.ScheduleDetail;
import lombok.Getter;

@Getter
public class ScheduleDetailBase {

    private final Long id;

    private final String detail;

    public ScheduleDetailBase(ScheduleDetail scheduleDetail) {
        this.id = scheduleDetail.getId();
        this.detail = scheduleDetail.getDetail();
    }
}
