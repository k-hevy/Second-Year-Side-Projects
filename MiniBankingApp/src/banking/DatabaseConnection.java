package banking;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
	
	static Connection con;
	
	public static Connection getConnection() {
		try {
			String mysqlJDBCDriver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/BANK";
			String user = "root";
			String pass = "SQLniKean987654321";
			
			Class.forName(mysqlJDBCDriver);
			con = DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
			System.out.println("Connection Unsuccesful: " + e.getMessage());
		}
		return con;
	}
}