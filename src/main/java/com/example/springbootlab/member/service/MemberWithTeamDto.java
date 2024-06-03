package com.example.springbootlab.member.service;

import com.example.springbootlab.member.domain.Member;
import com.example.springbootlab.team.service.TeamDto;

public record MemberWithTeamDto(
        MemberDto memberDto,
        TeamDto teamDto
) {

    public static MemberWithTeamDto from(Member member) {
        return new MemberWithTeamDto(
                MemberDto.from(member),
                TeamDto.from(member.getTeam())
        );
    }
}
