package com.lib.common.repository;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Executor for SQL filter operation
 * */
public class SpecificationFilterExecutor {

    /**
     * Perform SQL filter on specific column or on multi column<br/>
     *
     * {@link E}:&nbsp; &nbsp;Type of entity<br/>
     * {@link T}:&nbsp; &nbsp;Type of column data
     * */
    public static <T, E> Specification<E> filter(FilterMetadata<T> metadata) {

        /* Validation */

        FilterMetadata.FilterType filterType = metadata.getFilterType();

        /* NOT case */
        if (metadata.getSubFilterMetadata() != null) {
            if (filterType != null && filterType.isNot()) {
                return Specification.not(groupFilter(metadata.getSubFilterMetadata()));
            } else if (filterType == null) {
                return groupFilter(metadata.getSubFilterMetadata());
            }
        }

        /* Other case */
        return (root, query, builder) -> switch (filterType) {
            case EQUAL -> builder.equal(root.get(metadata.getFieldName()), metadata.getValueToCompare());
            case NOT_EQUAL -> builder.notEqual(root.get(metadata.getFieldName()), metadata.getValueToCompare());
            case GREATER -> builder.greaterThan(root.get(metadata.getFieldName()), metadata.getFieldName());
            case GREATER_THAN_OR_EQUAL -> builder.greaterThanOrEqualTo(root.get(metadata.getFieldName()), metadata.getFieldName());
            case LESSER -> builder.lessThan(root.get(metadata.getFieldName()), metadata.getFieldName());
            case LESSER_THAN_OR_EQUAL -> builder.lessThanOrEqualTo(root.get(metadata.getFieldName()), metadata.getFieldName());
            case LIKE -> builder.like(root.get(metadata.getFieldName()), (String) metadata.getValueToCompare());
            case NOT_LIKE -> builder.notLike(root.get(metadata.getFieldName()), (String) metadata.getValueToCompare());
            case IS_TRUE -> builder.isTrue(root.get(metadata.getFieldName()));
            case IS_FALSE -> builder.isFalse(root.get(metadata.getFieldName()));
            case IS_NULL -> builder.isNull(root.get(metadata.getFieldName()));
            case IS_NOT_NULL -> builder.isNotNull(root.get(metadata.getFieldName()));
            // For cover all case purpose, will be removed later when using validation
            case NOT -> null;
        };
    }

    /**
     * Aggregate SQL filter on multiple column<br/>
     *
     * {@link E}:&nbsp; &nbsp;Type of entity
     * */
    public static <E> Specification<E> groupFilter (GroupFilterMetadata metadata) {

        /* Validation */

        List<FilterMetadata<?>> subMetadata = metadata.getFilters();
        if (subMetadata == null || subMetadata.isEmpty()) {
            return null;
        }

        List<Specification<E>> specs = new ArrayList<>();
        for (FilterMetadata<?> m : subMetadata) {
            Specification<E> spec = filter(m);
            specs.add(spec);
        }

        return metadata.getGroupType().isAnd() ? Specification.allOf(specs) : Specification.anyOf(specs);
    }
}
