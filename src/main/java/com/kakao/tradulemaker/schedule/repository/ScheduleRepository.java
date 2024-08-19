package com.kakao.tradulemaker.schedule.repository;

import com.kakao.tradulemaker.member.entity.Member;
import com.kakao.tradulemaker.schedule.dto.res.ScheduleRes;
import com.kakao.tradulemaker.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
  Optional<Schedule> findByIdAndMember(Long scheduleId, Member member);
}
