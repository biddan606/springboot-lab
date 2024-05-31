package com.example.springbootlab.member.service;

public record MemberCreateParam(
        String name,
        int age,
        Long teamId
) {

}
