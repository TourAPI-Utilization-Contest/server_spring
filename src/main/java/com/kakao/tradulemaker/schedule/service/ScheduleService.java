package com.kakao.tradulemaker.schedule.service;

import com.kakao.tradulemaker.common.Exception.ServiceDefinedException;
import com.kakao.tradulemaker.common.Exception.config.ErrorCode;
import com.kakao.tradulemaker.member.dto.res.base.MemberDto;
import com.kakao.tradulemaker.oauth.service.KakaoApi;
import com.kakao.tradulemaker.schedule.dto.res.ScheduleRes;
import com.kakao.tradulemaker.schedule.entity.Schedule;
import com.kakao.tradulemaker.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

  private final ScheduleRepository scheduleRepository;

  private final KakaoApi kakaoApi;

  @Transactional(readOnly = true)
  public ScheduleRes readSchedule(
          Long scheduleId,
          boolean containsUser,
          String baseUri,
          String accessToken
  ) {
    Schedule schedule = readOneById(scheduleId);

    MemberDto memberDto = null;
    if (containsUser)
      memberDto = kakaoApi.getMember(baseUri, accessToken);


    return ScheduleRes.builder()
            .schedule(schedule)
            .memberDto(memberDto)
            .build();
  }

  @Transactional(readOnly = true)
  public List<ScheduleRes> readSchedules() {

    return (List<ScheduleRes>) new ArrayList<>().stream().map(o -> {
      return new Object();
    });
  }

  private Schedule readOneById(Long scheduleId) {
    return scheduleRepository.findById(scheduleId)
            .orElseThrow(
                    () -> new ServiceDefinedException(ErrorCode.NOT_FOUND)
            );
  }
}
