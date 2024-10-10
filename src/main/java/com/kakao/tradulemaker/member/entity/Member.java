package com.kakao.tradulemaker.member.entity;

import com.kakao.tradulemaker.schedule.entity.Schedule;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Entity(name = "member")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

//  For official test
  @Column(name = "nickname")
  private String nickname;

  @Column(name = "profile_url")
  private String profileUrl;

  @Column(name = "email")
  private String email;

  @Column(name = "access_token")
  private String accessToken;

  @Column(name = "password")
  private String password;

  @Setter
  @Column(name = "extra")
  private String extra;

  @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<Schedule> schedules = new ArrayList<>();

}
