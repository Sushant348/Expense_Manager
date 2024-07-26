package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
	
	 private static Connection  connection;
	 private static String url="jdbc:mysql://localhost:3306/servlet";
	 private static String userName="root";
	 private static String userPassword="sql123";
	 
	   private ConnectionHelper() {
		   try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection =DriverManager.getConnection("jdbc:mysql://localhost:3306/servlet","root","sql123");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	   }
	    
	    public static Connection getConnection()
	    {
	        if (connection == null) {
	        	new ConnectionHelper();
	        	
	        }
	        return connection;
	    }

}
