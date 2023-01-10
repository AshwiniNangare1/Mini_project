package com.velocity.miniProject;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionConnectivity {
	public Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mini_project?autoReconnect=true&useSSL=false", "root", "root");
		}catch (Exception e) {
			e.printStackTrace();
		}
	return connection;
	}
}
