package com.kakao.tradulemaker.schedule.repository;

import com.kakao.tradulemaker.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
