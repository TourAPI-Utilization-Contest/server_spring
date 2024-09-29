package com.kakao.tradulemaker.oauth.dto.res.base;

import com.kakao.tradulemaker.member.entity.Member;
import com.kakao.tradulemaker.member.service.MemberService;
import lombok.Getter;

@Getter
public class TokenTest {

    private final String accessToken;

    public TokenTest(Member member) {
        this.accessToken = member.getAccessToken();
    }
}
