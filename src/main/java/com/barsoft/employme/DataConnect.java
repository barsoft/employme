package com.barsoft.employme;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataConnect {

	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Connection con = DriverManager.getConnection(
			// "jdbc:mysql://127.12.5.2:3306/employmeex?useUnicode=yes&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull",
			// "adminpgfczzm", "SDTCsF8N54Yf");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1/employme?useUnicode=yes&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull",
					"root", "");
			return con;
		} catch (Exception ex) {
			System.out.println("Database.getConnection() Error -->" + ex.getMessage());
			return null;
		}
	}

	public static void close(Connection con) {
		try {
			con.close();
		} catch (Exception ex) {
		}
	}
}