package com.kakao.tradulemaker.member.repository;

import com.kakao.tradulemaker.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
