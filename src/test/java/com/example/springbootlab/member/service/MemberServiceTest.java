package com.example.springbootlab.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springbootlab.member.domain.Member;
import com.example.springbootlab.member.repository.MemberRepository;
import com.example.springbootlab.team.domain.Team;
import com.example.springbootlab.team.service.TeamCreateParam;
import com.example.springbootlab.team.service.TeamRepository;
import com.example.springbootlab.team.service.TeamService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

    @DisplayName("파라미터가 유효하다면, 멤버를 생성한다.")
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

    @DisplayName("멤버가 존재한다면, 멤버의 아이디를 통해 불러온다.")
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

    @DisplayName("멤버의 이름, 팀의 이름 오름차순으로 멤버와 팀으로 이루어진 페이지들을 반환한다.")
    @Test
    void findSortedMemberWithTeamPages_returnMemberWithTeamPagesSortedByName() {
        // given
        // 팀 이름을 Team1, Team2, ...로 생성한다.
        int teamCount = 10;
        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < teamCount; i++) {
            teams.add(Team.builder()
                    .name("team" + i)
                    .build());
        }
        teamRepository.saveAll(teams);

        // 멤버를 (Member1, Team1), (Member1, Team2), ..., (Member5, Team1), (Member5, Team2), ...로 생성한다.
        int memberNameCount = 10;
        List<Member> members = new ArrayList<>();
        for (int teamI = 0; teamI < teamCount; teamI++) {
            for (int memberNameI = 0; memberNameI < memberNameCount; memberNameI++) {
                members.add(Member.builder()
                        .name("member" + memberNameI)
                        .age(10)
                        .team(teams.get(teamI))
                        .build());
            }
        }
        memberRepository.saveAll(members);

        Pageable pageable = PageRequest.of(1, 10, Sort.by(
                Sort.Order.asc("member.name"),
                Sort.Order.asc("team.name")
        ));

        // when
        Page<MemberWithTeamDto> result = memberService.findSortedMemberWithTeamPages(pageable);

        // then
        List<MemberWithTeamDto> content = result.getContent();
        Assertions.assertAll("멤버의 이름, 팀의 이름 오름차순 결과",
                () -> assertThat(content).size().isEqualTo(10),
                () -> assertThat(content.get(0).memberDto().name()).isEqualTo("member1"),
                () -> assertThat(content.get(0).teamDto().name()).isEqualTo("team0"),
                () -> assertThat(result.getTotalPages()).isEqualTo(10),
                () -> assertThat(result.getTotalElements()).isEqualTo(100)
        );
    }
}
