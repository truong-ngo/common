package com.lib.common.repository;

import lombok.Data;

/**
 * Filter metadata on one or multi column
 * */
@Data
public class FilterMetadata<T> {
    private String fieldName;
    private T valueToCompare;
    private FilterType filterType;
    private GroupFilterMetadata subFilterMetadata;

    /**
     * Atomic filter constructor
     * */
    public FilterMetadata(String fieldName, T valueToCompare, FilterType filterType) {
        this.fieldName = fieldName;
        this.valueToCompare = valueToCompare;
        this.filterType = filterType;
    }

    /**
     * Constructor for NOT filter type
     * */
    public FilterMetadata(FilterType filterType, GroupFilterMetadata subFilterMetadata) {
        this.filterType = filterType;
        this.subFilterMetadata = subFilterMetadata;
    }

    /**
     * Multi column filter constructor
     * */
    public FilterMetadata(GroupFilterMetadata subFilterMetadata) {
        this.subFilterMetadata = subFilterMetadata;
    }

    public enum FilterType {
        NOT, // Special case
        EQUAL, NOT_EQUAL, GREATER, GREATER_THAN_OR_EQUAL, LESSER, LESSER_THAN_OR_EQUAL,
        LIKE, NOT_LIKE, IS_TRUE, IS_FALSE, IS_NOT_NULL, IS_NULL;

        public boolean isNot() {
            return this.equals(NOT);
        }
    }
}
