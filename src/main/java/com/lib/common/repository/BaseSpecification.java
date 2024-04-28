package com.lib.common.repository;

import org.springframework.data.jpa.domain.Specification;

/**
 * Base {@link Specification} of {@link E} entity
 * */
public interface BaseSpecification<E> {

    /**
     * Get atomic filter
     * */
    default <T> FilterMetadata<T> getAtomicFilter(String fieldName, T value, FilterMetadata.FilterType filterType) {
        return new FilterMetadata<>(fieldName, value, filterType);
    }

    /**
     * SQL filter
     * */
    default <T> Specification<E> filter(FilterMetadata<T> metadata) {
        return SpecificationFilterExecutor.filter(metadata);
    }

    /**
     * SQL group filter
     * */
    default Specification<E> groupFilter(GroupFilterMetadata metadata) {
        return SpecificationFilterExecutor.groupFilter(metadata);
    }
}
