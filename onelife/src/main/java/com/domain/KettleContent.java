package com.domain;

public class KettleContent {
    private String db_name_out;
    private String ip_out;
    private String sqltype_out;
    private String sid_out;
    private String port_out;
    private String user_out;
    private String pswd_out;

    private String db_name_in;
    private String ip_in;
    private String sqltype_in;
    private String sid_in;
    private String port_in;
    private String user_in;
    private String pswd_in;

    private String sql;
    private String fields;

    @Override
    public String toString() {
        return "KettleContent{" +
                "db_name_out='" + db_name_out + '\'' +
                ", ip_out='" + ip_out + '\'' +
                ", sqltype_out='" + sqltype_out + '\'' +
                ", sid_out='" + sid_out + '\'' +
                ", port_out='" + port_out + '\'' +
                ", user_out='" + user_out + '\'' +
                ", pswd_out='" + pswd_out + '\'' +
                ", db_name_in='" + db_name_in + '\'' +
                ", ip_in='" + ip_in + '\'' +
                ", sqltype_in='" + sqltype_in + '\'' +
                ", sid_in='" + sid_in + '\'' +
                ", port_in='" + port_in + '\'' +
                ", user_in='" + user_in + '\'' +
                ", pswd_in='" + pswd_in + '\'' +
                ", sql='" + sql + '\'' +
                ", fields='" + fields + '\'' +
                '}';
    }

    public String getDb_name_out() {
        return db_name_out;
    }

    public void setDb_name_out(String db_name_out) {
        this.db_name_out = db_name_out;
    }

    public String getIp_out() {
        return ip_out;
    }

    public void setIp_out(String ip_out) {
        this.ip_out = ip_out;
    }

    public String getSqltype_out() {
        return sqltype_out;
    }

    public void setSqltype_out(String sqltype_out) {
        this.sqltype_out = sqltype_out;
    }

    public String getSid_out() {
        return sid_out;
    }

    public void setSid_out(String sid_out) {
        this.sid_out = sid_out;
    }

    public String getPort_out() {
        return port_out;
    }

    public void setPort_out(String port_out) {
        this.port_out = port_out;
    }

    public String getUser_out() {
        return user_out;
    }

    public void setUser_out(String user_out) {
        this.user_out = user_out;
    }

    public String getPswd_out() {
        return pswd_out;
    }

    public void setPswd_out(String pswd_out) {
        this.pswd_out = pswd_out;
    }

    public String getDb_name_in() {
        return db_name_in;
    }

    public void setDb_name_in(String db_name_in) {
        this.db_name_in = db_name_in;
    }

    public String getIp_in() {
        return ip_in;
    }

    public void setIp_in(String ip_in) {
        this.ip_in = ip_in;
    }

    public String getSqltype_in() {
        return sqltype_in;
    }

    public void setSqltype_in(String sqltype_in) {
        this.sqltype_in = sqltype_in;
    }

    public String getSid_in() {
        return sid_in;
    }

    public void setSid_in(String sid_in) {
        this.sid_in = sid_in;
    }

    public String getPort_in() {
        return port_in;
    }

    public void setPort_in(String port_in) {
        this.port_in = port_in;
    }

    public String getUser_in() {
        return user_in;
    }

    public void setUser_in(String user_in) {
        this.user_in = user_in;
    }

    public String getPswd_in() {
        return pswd_in;
    }

    public void setPswd_in(String pswd_in) {
        this.pswd_in = pswd_in;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }
}
