package com.domain.basics;

/**
 * group by统一放到where过滤条件里
 */
public class Where {
    private String filterCondition;
    private String filterCnCondition;

    @Override
    public String toString() {
        return "Where{" +
                "filterCondition='" + filterCondition + '\'' +
                ", filterCnCondition='" + filterCnCondition + '\'' +
                '}';
    }

    public String getFilterCondition() {
        return filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    public String getFilterCnCondition() {
        return filterCnCondition;
    }

    public void setFilterCnCondition(String filterCnCondition) {
        this.filterCnCondition = filterCnCondition;
    }
}
