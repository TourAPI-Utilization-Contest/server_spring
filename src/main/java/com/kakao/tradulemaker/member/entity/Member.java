package com.kakao.tradulemaker.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity(name = "user")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id")
  private String userId;

}
