package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Client;

public class ClientDAO{
	
	protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
	private static final String insertStatementString = "INSERT INTO client (clientID,name,address)"
			+ " VALUES (?,?,?)";
	private static final String deleteStatementString= "delete from client where clientID= ?";
	private final static String findStatementStringID = "SELECT * FROM client where clientID = ?";
	private final static String findStatementStringName = "SELECT * FROM client where name = ?";
	
	
	/**
	 * metoda de cautare client dupa id
	 * @param clientID - id ul clientului pe care il cautam
	 * @return - clientul
	 */
	
	public static Client findById(int clientID)
	{
		Client toReturn = null;
		
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try {
			findStatement = dbConnection.prepareStatement(findStatementStringID);
			findStatement.setLong(1, clientID);
			rs = findStatement.executeQuery();
			rs.next();

			String name = rs.getString("name");
			String address = rs.getString("address");
			toReturn = new Client(clientID,name,address);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING,"StudentDAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close((com.mysql.jdbc.Connection) dbConnection);
		}
		
		return toReturn;
	}
	
	/**
	 * cautare dupa nume
	 * @param name - nume client cautat
	 * @return - clientul cu numele specificat
	 */
	public static Client findByName(String name)
	{
		Client toReturn = null;
		
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try {
			findStatement = dbConnection.prepareStatement(findStatementStringName);
			findStatement.setString(1, name);
			rs = findStatement.executeQuery();
			rs.next();
			int clientID= rs.getInt("clientID");
			String address = rs.getString("address");
			toReturn = new Client(clientID,name,address);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING,"ClientDAO:findClientByName" + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close((com.mysql.jdbc.Connection) dbConnection);
		}
		
		return toReturn;
	}
	
	/**
	 * metoda de inserare client
	 * @param client - clientul de inserat
	 * @return - id client inserat
	 */
	public static int insertClient(Client client)
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setLong(1,client.getClientID());
			insertStatement.setString(2, client.getName());
			insertStatement.setString(3, client.getAddress());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close((com.mysql.jdbc.Connection) dbConnection);
		}
		return insertedId;
	}
	
	/**
	 * metoda de stergere client
	 * @param client - clientul de sters
	 * @return - id client
	 */
	
	public static int deleteClient(Client client)
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int deletedID = -1;
		try {
			insertStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setLong(1,client.getClientID());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				deletedID = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:delete " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close((com.mysql.jdbc.Connection) dbConnection);
		}
		return deletedID;
	}
	
	/**
	 * metoda de selectie totala
	 * @return lista cu toate obiectele
	 */
	 public static List<Client> selectAllClients()
	 {
		 List<Client> clients= new ArrayList<Client>();
		 
		 	Connection dbConnection = ConnectionFactory.getConnection();

			PreparedStatement selectAllStatement = null;
			String findAllStatement= "SELECT * FROM test.client";
			
			try {
				selectAllStatement = dbConnection.prepareStatement(findAllStatement);
				ResultSet rs = selectAllStatement.executeQuery();
				
					if(rs.next())
					{
						do {
							
						Client aux=new Client();
						int id= rs.getInt(1);
						String name=rs.getString(2);
						String address=rs.getString(3);

						System.out.println(id+" "+name+" "+address);
						clients.add(new Client(id,name,address));
						
					}while(rs.next());
					
					}
					
				
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "ClientDAO:findAllClients " + e.getMessage());
			} finally {
				ConnectionFactory.close(selectAllStatement);
				ConnectionFactory.close((com.mysql.jdbc.Connection) dbConnection);
				
			}
			return clients;
			
	 }

}
