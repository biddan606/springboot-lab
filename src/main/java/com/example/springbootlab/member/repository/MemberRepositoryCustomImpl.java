package com.example.springbootlab.member.repository;


import com.example.springbootlab.member.domain.Member;
import com.example.springbootlab.member.domain.QMember;
import com.example.springbootlab.team.domain.QTeam;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final QMember member = QMember.member;
    private final QTeam team = QTeam.team;

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Member> findSortedMemberWithTeamPages(Pageable pageable) {
        List<Member> content = jpaQueryFactory
                .selectFrom(member)
                .leftJoin(member.team, team).fetchJoin()
                .orderBy(getOrderSpecifies(pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<Member> countQuery = jpaQueryFactory
                .selectFrom(member);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private OrderSpecifier<?>[] getOrderSpecifies(Sort sort) {
        return sort.stream()
                .map(order -> {
                    String property = order.getProperty();
                    PathBuilder<?> entityPath = property.startsWith("team.")
                            ? new PathBuilder<>(team.getType(), team.getMetadata())
                            : new PathBuilder<>(member.getType(), member.getMetadata());

                    OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(
                            order.isAscending() ? Order.ASC : Order.DESC,
                            Expressions.path(String.class, entityPath,
                                    property.substring(property.indexOf('.') + 1))
                    );

                    return order.isAscending()
                            ? orderSpecifier.nullsLast()
                            : orderSpecifier.nullsFirst();
                })
                .toArray(OrderSpecifier[]::new);
    }
}
