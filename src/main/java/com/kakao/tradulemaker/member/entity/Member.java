package com.kakao.tradulemaker.member.entity;

import com.kakao.tradulemaker.schedule.entity.Schedule;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
  private Long id;

  @OneToMany(mappedBy = "schedule", orphanRemoval = true, cascade = CascadeType.PERSIST)
  private List<Schedule> schedules = new ArrayList<>();
}
