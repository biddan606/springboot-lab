package com.example.springbootlab.config.querydsl;

import com.example.springbootlab.support.QuerydslOrderSupport;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class QuerydslConfig {

    private final EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public QuerydslOrderSupport querydslOrderSupport() {
        return new QuerydslOrderSupport();
    }
}
