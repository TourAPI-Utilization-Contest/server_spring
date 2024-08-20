package com.kakao.tradulemaker.member.service;

import com.kakao.tradulemaker.common.Exception.ServiceDefinedException;
import com.kakao.tradulemaker.common.Exception.config.ErrorCode;
import com.kakao.tradulemaker.member.entity.Member;
import com.kakao.tradulemaker.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  /**
   * 회원 조회
   *
   * @param memberId memberId from Kakao
   * @return Member
   * @throws ServiceDefinedException when members cannot be found
   */
  public Member readMemberById(Long memberId) {
    return memberRepository.findById(memberId)
            .orElseThrow(() -> new ServiceDefinedException(ErrorCode.NOT_FOUND));
  }

}
