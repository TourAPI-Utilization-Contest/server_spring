package com.kakao.tradulemaker.schedule.repository;

import com.kakao.tradulemaker.schedule.entity.Schedule;
import com.kakao.tradulemaker.schedule.entity.ScheduleDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleDetailRepository extends JpaRepository<ScheduleDetail, Long> {
    Optional<ScheduleDetail> findByIdAndSchedule(Long scheduleDetailId, Schedule schedule);
}
