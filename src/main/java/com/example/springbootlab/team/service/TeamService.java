package com.example.springbootlab.team.service;

import com.example.springbootlab.team.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public Long create(TeamCreateParam param) {
        Team newTeam = Team.builder().name(param.name()).build();

        return teamRepository.save(newTeam).getId();
    }

    public TeamDto getById(Long teamId) {
        Team foundTeam = teamRepository.getById(teamId);

        return TeamDto.from(foundTeam);
    }
}
