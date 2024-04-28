package com.lib.common.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Base interface of entity mapper
 * @param <E> Entity type
 * @param <D> Dto type
 * */
public interface EntityMapper<E, D> {

    /**
     * Map Dto to Entity
     * */
    E toEntity(D dto);

    /**
     * Map Entity to Dto
     * */
    D toDto(E entity);

    /**
     * Update dto value, ignored null
     * */
    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    D partialUpdate(D dto);
}
