package com.example.springbootlab.member.repository;


import static com.example.springbootlab.member.domain.QMember.member;
import static com.example.springbootlab.team.domain.QTeam.team;

import com.example.springbootlab.member.domain.Member;
import com.example.springbootlab.support.QuerydslOrderSupport;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QuerydslOrderSupport querydslOrderSupport;

    @Override
    public Page<Member> findSortedMemberWithTeamPages(Pageable pageable) {
        List<Member> content = jpaQueryFactory
                .selectFrom(member)
                .leftJoin(member.team, team).fetchJoin()
                .orderBy(querydslOrderSupport.getOrderSpecifiers(pageable.getSort(), member))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<Member> countQuery = jpaQueryFactory
                .selectFrom(member);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }
}
