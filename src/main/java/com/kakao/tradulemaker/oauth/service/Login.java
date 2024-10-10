package com.kakao.tradulemaker.oauth.service;

import com.kakao.tradulemaker.common.Exception.ServiceDefinedException;
import com.kakao.tradulemaker.common.Exception.config.ErrorCode;
import com.kakao.tradulemaker.member.entity.Member;
import com.kakao.tradulemaker.oauth.dto.req.LoginTest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.kakao.tradulemaker.member.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class Login {

    @Value("${server.admin-token}")
    private String adminToken;
    @Value("${server.profileUrl}")
    private String profileUrl;

    private final MemberRepository memberRepository;


    @Transactional(readOnly = false)
    public Member registerMember(LoginTest dto) {
        // 이메일 중복 체크
        if (memberRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ServiceDefinedException(ErrorCode.NOT_FOUND);
        }

        // Member 객체 생성 및 저장
        Member member = Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .accessToken(adminToken)
                .profileUrl(profileUrl)
                .build();

        return memberRepository.save(member);
    }


    public Member readMemberByEmail(String email) {
        return (Member) memberRepository.findByEmail(email)
                .orElseThrow(() -> new ServiceDefinedException(ErrorCode.NOT_FOUND));
    }

    public void updateMember(Long memberId, String extra) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ServiceDefinedException(ErrorCode.NOT_FOUND));
        member.setExtra(extra);
        // Add logic for any other fields you want to update
        memberRepository.save(member);
    }
}
