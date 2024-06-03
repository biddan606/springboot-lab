package com.example.springbootlab.support;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.data.domain.Sort;

public class QuerydslOrderSupport {

    public OrderSpecifier<?>[] getOrderSpecifiers(Sort sort, EntityPathBase<?> defaultEntity) {
        return sort.stream()
                .map(order -> {
                    String property = order.getProperty();
                    PathBuilder<?> entityPath = getPathBuilderFromProperty(property, defaultEntity);

                    OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(
                            order.isAscending() ? Order.ASC : Order.DESC,
                            Expressions.path(String.class, entityPath, getPropertyPath(property))
                    );

                    return order.isAscending()
                            ? orderSpecifier.nullsLast()
                            : orderSpecifier.nullsFirst();
                })
                .toArray(OrderSpecifier[]::new);
    }

    private PathBuilder<?> getPathBuilderFromProperty(String property, EntityPathBase<?> defaultEntity) {
        if (property.contains(".")) {
            String className = getClassName(property);
            EntityPathBase<?> entityPathBase = EntityPathEnum.getQClassByClassName(className);
            return new PathBuilder<>(entityPathBase.getType(), entityPathBase.getMetadata());
        } else {
            return new PathBuilder<>(defaultEntity.getType(), defaultEntity.getMetadata());
        }
    }

    private String getClassName(String property) {
        int dotIndex = property.indexOf('.');
        if (dotIndex == -1) {
            throw new IllegalArgumentException("예상되는 속성 이름에는 '.'이 포함되어 있지만, 실제로는 포함되어 있지 않습니다: " + property);
        }
        return property.substring(0, dotIndex);
    }

    private String getPropertyPath(String property) {
        return property.contains(".") ? property.substring(property.indexOf('.') + 1) : property;
    }
}
