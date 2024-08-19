package com.kakao.tradulemaker.schedule.service;

import com.kakao.tradulemaker.common.Exception.ServiceDefinedException;
import com.kakao.tradulemaker.common.Exception.config.ErrorCode;
import com.kakao.tradulemaker.member.entity.Member;
import com.kakao.tradulemaker.member.service.MemberService;
import com.kakao.tradulemaker.schedule.entity.Schedule;
import com.kakao.tradulemaker.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

  private final MemberService memberService;

  private final ScheduleRepository scheduleRepository;

  @Transactional(readOnly = true)
  public Schedule readSchedule(
          Long scheduleId,
          Long memberId
  ) {
    Member member = memberService.readMemberById(memberId);

    return scheduleRepository.findByIdAndMember(scheduleId, member)
            .orElseThrow(() -> new ServiceDefinedException(ErrorCode.FORBIDDEN_TO_ACCESS));
  }
}
