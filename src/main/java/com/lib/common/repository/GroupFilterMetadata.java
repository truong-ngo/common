package com.lib.common.repository;

import lombok.Data;

import java.util.List;

/**
 * Filter metadata on group of filter and aggregate pattern
 * */
@Data
public class GroupFilterMetadata {

    private List<FilterMetadata<?>> filters;
    private GroupType groupType;

    public GroupFilterMetadata(List<FilterMetadata<?>> filters, GroupType groupType) {
        this.filters = filters;
        this.groupType = groupType;
    }

    public enum GroupType {
        AND, OR;

        public boolean isAnd() {
            return this.equals(AND);
        }
    }
}
