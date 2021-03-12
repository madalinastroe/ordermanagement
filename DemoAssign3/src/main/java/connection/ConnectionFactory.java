package connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.jdbc.Connection;

public class ConnectionFactory {
	
	private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String DBURL = "jdbc:mysql://localhost:3306/test?useSSL=false";
	private static final String USER = "root";
	private static final String PASS = "Hanovra123";
	
	private static ConnectionFactory singleInstance = new ConnectionFactory();
	/**
	 * Constructorul clasei Connection Factory
	 */
	
	private ConnectionFactory() {
		try {
			
			Class.forName(DRIVER);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * metoda de create connection
	 * @return conexiunea
	 */
	private Connection createConnection() {
		Connection connection = null;
		try {
			connection = (Connection) DriverManager.getConnection(DBURL, USER, PASS);
			System.out.println("S-a realizat conexiunea la baza de date.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public static Connection getConnection() {
		return singleInstance.createConnection();
	}

	/**
	 * metoda inchidere conexiune
	 * @param connection conexiunea care se va inchide
	 */
	public static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * inchide statement
 	 * @param statement - statementul care se va inchide
	 */
	public static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();				}
		}
	}

	/**
	 * inchide result set
	 * @param resultSet - result set ul care se va inchide
	 */
	public static void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
		}
		}
	}
	

}
