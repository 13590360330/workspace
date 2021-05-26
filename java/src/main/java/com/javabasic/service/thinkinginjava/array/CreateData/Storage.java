package com.javabasic.service.thinkinginjava.array.CreateData;

import com.javabasic.dao.Generator;
import com.javabasic.dao.JdbcConnection;
import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * TODO 测试数据存储介质
 */
public class Storage {

    public void Dbms(String databaseType, String database, Class<? extends Generator>[] a, String tablename)
            throws InstantiationException, IllegalAccessException, SQLException {
        JdbcConnection jdbcConnection =
                new JdbcConnection( "root", "root", "127.0.0.1", "3306", database, databaseType );
        Connection dbConnection = jdbcConnection.getDbConnection();
        for (int i = 0; i < 1000; i++) {
            CtDate ct = new CtDate( a );
            StringBuilder sql = new StringBuilder( "insert into " + tablename + " values(\"" );
            String join = StringUtils.join( ct.getDate(), "\",\"" );
            sql.append( join );
            sql.append( "\")" );
            System.out.println( sql.toString() );
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = dbConnection.prepareStatement( sql.toString() );
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
            }
        }
        dbConnection.close();
        jdbcConnection.closeDbConnection();
    }
}
