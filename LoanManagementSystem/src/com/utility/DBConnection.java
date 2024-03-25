package com.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static Connection conn;

	public static Connection getDBConn() throws ClassNotFoundException, SQLException {

		String usernameDb = "root";
		String passwordDb = "";
		String urlDb = "jdbc:mysql://localhost:3306/loanmanagementsystem";
		String driverName = "com.mysql.jdbc.Driver";
		
		Class.forName(driverName);
		
		conn = DriverManager.getConnection(urlDb,usernameDb, passwordDb );
		
		return conn;

	}
	
	public static void dbClose() throws SQLException {
		conn.close();
	}
	
}
