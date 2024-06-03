package com.example.springbootlab.member.repository;

import com.example.springbootlab.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    @Transactional(readOnly = true)
    default Member getById(Long memberId) {
        return findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버 ID입니다."));
    }
}
