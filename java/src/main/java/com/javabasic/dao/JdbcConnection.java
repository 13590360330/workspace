package com.javabasic.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection {

	private  Connection conn = null;
	private  String driver = "" ;//????
	private  String url = ""; //?????????
	private  String username = "";
	private  String password = "";	

	
	/**
	 * ?????????????
	 * @param userName
	 * @param passWord
	 * @param ip
	 * @param port
	 * @param dbName
	 * @param dbType
	 */
	public  JdbcConnection(String userName,String passWord,String ip,String port,String dbName,String dbType){
		dbType = dbType.toLowerCase();
		username = userName;
		password = passWord;
		if("oracle".equals(dbType)){
			driver = "oracle.jdbc.driver.OracleDriver";
			url = "jdbc:oracle:thin:@"+ip+":"+port+":"+dbName;
		}else if ("sqlserver".equals(dbType)||"mssql".equals(dbType)){
			driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
			url = "jdbc:sqlserver://"+ip+":"+port+";DatabaseName="+dbName;
		}else if ("mysql".equals(dbType)){
			driver="com.mysql.jdbc.Driver";
			url = "jdbc:mysql://" + ip + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + passWord + "&useUnicode=true&characterEncoding=UTF8";
		}else {
			System.out.println("ERRO:?????????????");
		}
	}

	/**
	 * ???????????
	 * @return
	 */
	public  Connection getDbConnection(){
		if(conn==null){
			 try {
				 System.out.println(driver+";"+url);
				Class.forName(driver);
				conn = DriverManager.getConnection(url,username,password);
				//System.out.println("INFO:???????????!");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}
	
	/**
	 * ???????????
	 */
	public  void  closeDbConnection(){
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * ???????????????????????????
	 * @param userName
	 * @param passWord
	 * @param ip
	 * @param port
	 * @param dbName
	 * @param dbType
	 * @param sysCode
	 */
	public  JdbcConnection(String userName,String passWord,String ip,String port,String dbName,String dbType,String sysCode){
		String _driver = null;
		String _url = null;
		if("oracle".equals(dbType.toLowerCase())){
			_driver = "oracle.jdbc.driver.OracleDriver";
			_url = "jdbc:oracle:thin:@//"+ip+":"+port+"/"+dbName;
		}else if ("sqlserver".equals(dbType)||"mssql".equals(dbType)){
			_driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
			_url = "jdbc:sqlserver://"+ip+":"+port+";DatabaseName="+dbName;
		}else if ("mysql".equals(dbType)){
			_driver="com.mysql.jdbc.Driver";
			_url = "jdbc:mysql://" + ip + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + passWord + "&useUnicode=true&characterEncoding=UTF8";
		}else {
			System.out.println("ERRO:?????????????");
			return;
		}

	}

	public static void main(String[] args) {
		//String userName,String passWord,String ip,String port,String dbName,String dbType
		JdbcConnection jdbcConnection = new JdbcConnection("ods","ods","192.168.233.140","1521","orcl"
				,"oracle");
		System.out.println(jdbcConnection.getDbConnection());
	}
	
}
