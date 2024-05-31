package com.example.springbootlab.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springbootlab.team.service.TeamCreateParam;
import com.example.springbootlab.team.service.TeamRepository;
import com.example.springbootlab.team.service.TeamService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;



    @AfterEach
    void cleanUp() {
        memberRepository.deleteAllInBatch();
        teamRepository.deleteAllInBatch();
    }

    @DisplayName("파라미터가 유효하다면, 멤버를 생성할 수 있습니다.")
    @Test
    void create_success() {
        // given
        TeamCreateParam teamParam = new TeamCreateParam("teamA");
        Long teamId = teamService.create(teamParam);

        MemberCreateParam memberParam = new MemberCreateParam("memberA", 10, teamId);

        // when
        Long savedMemberId = memberService.create(memberParam);

        // then
        assertThat(savedMemberId).isNotNull();
    }

    @DisplayName("멤버가 존재한다면, 멤버의 아이디를 통해 불러올 수 있습니다.")
    @Test
    void getById_success() {
        // given
        TeamCreateParam teamParam = new TeamCreateParam("teamA");
        Long teamId = teamService.create(teamParam);

        MemberCreateParam memberParam = new MemberCreateParam("memberA", 10, teamId);
        Long savedMemberId = memberService.create(memberParam);

        // when
        MemberDto memberDto = memberService.getById(savedMemberId);

        // then
        Assertions.assertAll("멤버 속성값 비교",
                () -> assertThat(memberDto.memberId()).isEqualTo(savedMemberId),
                () -> assertThat(memberDto.name()).isEqualTo(memberParam.name()),
                () -> assertThat(memberDto.age()).isEqualTo(memberParam.age()),
                () -> assertThat(memberDto.teamId()).isEqualTo(teamId)
        );
    }
}
