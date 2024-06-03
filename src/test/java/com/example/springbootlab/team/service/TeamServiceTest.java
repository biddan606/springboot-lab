package com.example.springbootlab.team.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TeamServiceTest {

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamRepository teamRepository;

    @AfterEach
    void cleanUp() {
        teamRepository.deleteAllInBatch();
    }

    @DisplayName("파라미터가 유효하다면, 팀을 생성한다.")
    @Test
    void create_success() {
        // given
        TeamCreateParam teamAParam = new TeamCreateParam("teamA");

        // when
        Long savedTeamId = teamService.create(teamAParam);

        // then
        assertThat(savedTeamId).isNotNull();
    }

    @DisplayName("팀이 존재한다면, 팀의 아이디를 통해 불러온다")
    @Test
    void getById_success() {
        // given
        TeamCreateParam teamParam = new TeamCreateParam("teamA");
        Long savedTeamId = teamService.create(teamParam);

        // when
        TeamDto teamDto = teamService.getById(savedTeamId);

        // then
        Assertions.assertAll("팀 속성값 비교",
                () -> assertThat(teamDto.teamId()).isEqualTo(savedTeamId),
                () -> assertThat(teamDto.name()).isEqualTo(teamParam.name())
        );
    }
}
