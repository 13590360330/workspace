package com.domain;

public class Block {
    private TargetTable _targetTable;
    private SourceTable _sourceTable;
    private fromCondition _fromCondition;
    private WhereCondition _whereCondition;

    @Override
    public String toString() {
        return "Block{" +
                "_targetTable=" + _targetTable +
                ", _sourceTable=" + _sourceTable +
                ", _fromCondition=" + _fromCondition +
                ", _whereCondition=" + _whereCondition +
                '}';
    }

    public TargetTable get_targetTable() {
        return _targetTable;
    }

    public void set_targetTable(TargetTable _targetTable) {
        this._targetTable = _targetTable;
    }

    public SourceTable get_sourceTable() {
        return _sourceTable;
    }

    public void set_sourceTable(SourceTable _sourceTable) {
        this._sourceTable = _sourceTable;
    }

    public fromCondition get_fromCondition() {
        return _fromCondition;
    }

    public void set_fromCondition(fromCondition _fromCondition) {
        this._fromCondition = _fromCondition;
    }

    public WhereCondition get_whereCondition() {
        return _whereCondition;
    }

    public void set_whereCondition(WhereCondition _whereCondition) {
        this._whereCondition = _whereCondition;
    }
}
