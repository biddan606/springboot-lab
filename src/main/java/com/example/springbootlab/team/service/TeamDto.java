package com.example.springbootlab.team.service;

import com.example.springbootlab.team.domain.Team;

public record TeamDto(
        Long teamId,
        String name
) {

    public static TeamDto from(Team team) {
        return new TeamDto(team.getId(), team.getName());
    }
}
