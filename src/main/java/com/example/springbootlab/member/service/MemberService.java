package com.example.springbootlab.member.service;

import com.example.springbootlab.member.domain.Member;
import com.example.springbootlab.team.domain.Team;
import com.example.springbootlab.team.service.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public Long create(MemberCreateParam param) {
        Team team = teamRepository.getById(param.teamId());

        Member newMember = Member.builder()
                .name(param.name())
                .age(param.age())
                .team(team)
                .build();

        return memberRepository.save(newMember).getId();
    }

    public MemberDto getById(Long memberId) {
        Member foundMember = memberRepository.getById(memberId);

        return MemberDto.from(foundMember);
    }
}
