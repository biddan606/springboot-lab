package com.example.springbootlab.team.service;

import com.example.springbootlab.team.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Transactional(readOnly = true)
    default Team getById(Long teamId) {
        return findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀 ID입니다."));
    }
}
