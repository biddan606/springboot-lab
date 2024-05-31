package com.example.springbootlab.team.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Builder
    private Team(String name) {
        Assert.notNull(name, "팀의 이름은 null일 수 없습니다.");

        this.name = name;
    }
}
