package com.javabasic.dao;

import java.sql.*;

/**
 * 搭配TableAdd完成hive建表,
 */
public class HiveJdbcTest {

    public String driverName = "org.apache.hive.jdbc.HiveDriver";

    public String test(String tablename)
            throws SQLException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        Connection con = DriverManager.getConnection(
                "jdbc:hive2://192.168.233.140:10000/ods", "cloudera", "cloudera");
        Statement stmt = con.createStatement();
        String tableName = tablename;
        stmt.execute("drop table if exists " + tableName);
        String sql1="create table " + tableName +"(key int,column1 string)";
//        System.out.println(sql1);
        stmt.execute(sql1);
//        System.out.println("Create table success!");
        // show tables
        String sql = "select * from " + tableName + " limit 10";
//        System.out.println("Running: " + sql);
        ResultSet res = stmt.executeQuery(sql);
//        if (res.next()) {
//            System.out.println(res.getString(1));
//        }

        // describe table
        sql = "describe " + tableName;
//        System.out.println("Running: " + sql);
        res = stmt.executeQuery(sql);
//        while (res.next()) {
//            System.out.println(res.getString(1) + "\t" + res.getString(2));
//        }


        sql = "select * from " + tableName;
        res = stmt.executeQuery(sql);
//        while (res.next()) {
//            System.out.println(String.valueOf(res.getInt(1)) + "\t"
//                    + res.getString(2));
//        }

        sql = "select count(1) from " + tableName;
//        System.out.println("Running: " + sql);
        res = stmt.executeQuery(sql);
        String result=null;
        while (res.next()) {
            result=res.getString(1);
        }
        return result;
    }
}
