package com.example.springbootlab.member.service;

import com.example.springbootlab.member.domain.Member;

public record MemberDto(
        Long memberId,
        String name,
        int age,
        Long teamId
) {

    public static MemberDto from(Member member) {
        return new MemberDto(
                member.getId(),
                member.getName(),
                member.getAge(),
                member.getTeam().getId()
        );
    }

}
