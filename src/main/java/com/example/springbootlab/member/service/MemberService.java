package com.example.springbootlab.member.service;

import com.example.springbootlab.member.domain.Member;
import com.example.springbootlab.member.repository.MemberRepository;
import com.example.springbootlab.team.domain.Team;
import com.example.springbootlab.team.service.TeamRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    public Page<MemberWithTeamDto> findSortedMemberWithTeamPages(Pageable pageable) {
        Page<Member> membersPage = memberRepository.findSortedMemberWithTeamPages(pageable);

        List<MemberWithTeamDto> memberWithTeamDtos = membersPage
                .stream()
                .map(MemberWithTeamDto::from)
                .toList();

        return new PageImpl<>(memberWithTeamDtos, pageable, membersPage.getTotalElements());
    }
}
