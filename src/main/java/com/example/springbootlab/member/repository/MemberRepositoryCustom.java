package com.example.springbootlab.member.repository;

import com.example.springbootlab.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {

    Page<Member> findSortedMemberWithTeamPages(Pageable pageable);
}
