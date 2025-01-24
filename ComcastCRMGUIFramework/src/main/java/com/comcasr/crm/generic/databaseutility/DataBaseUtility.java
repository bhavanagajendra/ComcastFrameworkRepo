package com.comcasr.crm.generic.databaseutility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

public class DataBaseUtility {

	Connection con;
	
	public void getDbConnection(String url, String userName, String password) throws SQLException {
		
		try {
			Driver driver = new Driver();
			DriverManager.registerDriver(driver);
			
			con = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
		}
		
	}
	
	//Overloaded method to the above method. Here we have hard coded the url, username and password. Hence the user can use any one of these.
	public void getDbConnection() throws SQLException {
		
		try {
			Driver driver = new Driver();
			DriverManager.registerDriver(driver);
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projects", "root", "root");
		} catch (Exception e) {
		}
		
	}
	
	public void closeDbConnection() throws SQLException { 
		con.close();
	}
	
	public ResultSet executeSelectQuery(String query) throws SQLException {
		ResultSet result = null;
		try {
			Statement stat = con.createStatement();
			result = stat.executeQuery(query);
		} catch (Exception e) {
		}
		return result;
	}
	
	public int executeNonSelectQuery(String query) {
		int result = 0;
		try {
			Statement stat = con.createStatement();
			result = stat.executeUpdate(query);
		} catch (Exception e) {
		}
		return result;
	}
}