package com.kakao.tradulemaker.oauth.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginTest {

    private String email;

    private String password;

    private String nickname;
}
