package com.domain.basics;

public class Join {
    private String joinType;
    private String sys;
    private String secSourceTable;
    private String asName;
    private String onCondition;
    private String onCnCondition;

    @Override
    public String toString() {
        return "Join{" +
                "joinType='" + joinType + '\'' +
                ", sys='" + sys + '\'' +
                ", secSourceTable='" + secSourceTable + '\'' +
                ", asName='" + asName + '\'' +
                ", onCondition='" + onCondition + '\'' +
                ", onCnCondition='" + onCnCondition + '\'' +
                '}';
    }

    public String getJoinType() {
        return joinType;
    }

    public void setJoinType(String joinType) {
        this.joinType = joinType;
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public String getSecSourceTable() {
        return secSourceTable;
    }

    public void setSecSourceTable(String secSourceTable) {
        this.secSourceTable = secSourceTable;
    }

    public String getAsName() {
        return asName;
    }

    public void setAsName(String asName) {
        this.asName = asName;
    }

    public String getOnCondition() {
        return onCondition;
    }

    public void setOnCondition(String onCondition) {
        this.onCondition = onCondition;
    }

    public String getOnCnCondition() {
        return onCnCondition;
    }

    public void setOnCnCondition(String onCnCondition) {
        this.onCnCondition = onCnCondition;
    }
}
