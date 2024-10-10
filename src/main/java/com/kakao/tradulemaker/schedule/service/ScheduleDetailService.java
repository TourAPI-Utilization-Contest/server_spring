package com.kakao.tradulemaker.schedule.service;

import com.kakao.tradulemaker.common.Exception.ServiceDefinedException;
import com.kakao.tradulemaker.common.Exception.config.ErrorCode;
import com.kakao.tradulemaker.schedule.dto.req.ScheduleDetailReq;
import com.kakao.tradulemaker.schedule.entity.Schedule;
import com.kakao.tradulemaker.schedule.entity.ScheduleDetail;
import com.kakao.tradulemaker.schedule.repository.ScheduleDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleDetailService {

    private final ScheduleDetailRepository scheduleDetailRepository;

    @Transactional(readOnly = false)
    public Long create(
            ScheduleDetailReq scheduleDetailReq,
            Schedule schedule
    ) {
        ScheduleDetail scheduleDetail = ScheduleDetail.builder()
                .detail(scheduleDetailReq.getDetail())
                .schedule(schedule)
                .build();

        return scheduleDetailRepository.save(scheduleDetail).getId();
    }

    public ScheduleDetail readScheduleDetail(
            Schedule schedule,
            Long scheduleDetailId
    ) {
       return scheduleDetailRepository.findByIdAndSchedule(scheduleDetailId, schedule)
               .orElseThrow(() -> new ServiceDefinedException(ErrorCode.NOT_FOUND));
    }

    @Transactional(readOnly = false)
    public Long update(
            ScheduleDetail scheduleDetail,
            ScheduleDetailReq scheduleDetailReq
    ) {
        scheduleDetail.update(scheduleDetailReq);

        return scheduleDetail.getId();
    }

    public Long delete(
            Schedule schedule,
            Long scheduleDetailId
    ) {
        ScheduleDetail scheduleDetail = scheduleDetailRepository.findByIdAndSchedule(scheduleDetailId, schedule)
                .orElseThrow(() -> new ServiceDefinedException(ErrorCode.NOT_FOUND));

        scheduleDetailRepository.delete(scheduleDetail);

        return scheduleDetail.getId();
    }
}
