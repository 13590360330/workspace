package com.domain;

import com.domain.basics.Source;

import java.util.ArrayList;

public class SourceTable {
    private String target_tablename;
    private ArrayList<Source> sources;

    public SourceTable(String target_tablename, ArrayList<Source> sources) {
        this.target_tablename = target_tablename;
        this.sources = sources;
    }

    @Override
    public String toString() {
        return "SourceTable{" +
                "target_tablename='" + target_tablename + '\'' +
                ", sources=" + sources +
                '}';
    }

    public String getTarget_tablename() {
        return target_tablename;
    }

    public void setTarget_tablename(String target_tablename) {
        this.target_tablename = target_tablename;
    }

    public ArrayList<Source> getSources() {
        return sources;
    }

    public void setSources(ArrayList<Source> sources) {
        this.sources = sources;
    }
}
