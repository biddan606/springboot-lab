package com.example.springbootlab.support;

import com.example.springbootlab.member.domain.QMember;
import com.example.springbootlab.team.domain.QTeam;
import com.querydsl.core.types.dsl.EntityPathBase;

public enum EntityPathEnum {
    MEMBER("member", QMember.member),
    TEAM("team", QTeam.team);

    private final String className;
    private final EntityPathBase<?> qClass;

    EntityPathEnum(String className, EntityPathBase<?> qClass) {
        this.className = className;
        this.qClass = qClass;
    }

    public String getClassName() {
        return className;
    }

    public EntityPathBase<?> getQClass() {
        return qClass;
    }

    public static EntityPathBase<?> getQClassByClassName(String className) {
        for (EntityPathEnum entity : values()) {
            if (entity.getClassName().equals(className)) {
                return entity.getQClass();
            }
        }
        throw new IllegalArgumentException("클래스 이름과 일치하는 QClass가 없습니다: " + className);
    }
}
