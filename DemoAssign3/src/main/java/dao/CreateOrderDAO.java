package dao;

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
import model.CreateOrder;
import model.Order;
import model.Product;

public class CreateOrderDAO{
	
	protected static final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());
	private static final String insertStatementString = "INSERT INTO test.createorder (orderID,clientID,totalPrice)"
			+ " VALUES (?,?,?)";
	private static final String deleteStatementString= "delete from test.createorder where orderID= ? AND clientID=?";
	private final static String findStatementStringID = "SELECT * FROM test.createorder where orderID = ? AND clientID=?";
	private final static String findStatementStringClientID = "SELECT * FROM test.createorder where clientID = ?";
	private final static String updateStatement = "update test.createorder set  orderID=? clientID=? totalPrice=? where orderID=?";

	
	/**
	 * metoda de cautare createorder dupa id
	 * @param clientID - id client
	 * @return - createorder
	 */
	public static CreateOrder findByClientID(int clientID)
	{
		CreateOrder toReturn = null;
		
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try {
			findStatement = dbConnection.prepareStatement(findStatementStringClientID);
			//findStatement.setLong(1, CreateOrder.getOrderID());
			findStatement.setLong(1,clientID);
			
			rs = findStatement.executeQuery();
			rs.next();

			int orderID=rs.getInt("orderID");
			float price= rs.getFloat("totalPrice");
			toReturn = new CreateOrder(orderID, clientID,price);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING,"CreateOrderDAO:findByClientId " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close((com.mysql.jdbc.Connection) dbConnection);
		}
		
		return toReturn;
	}
	
	/**
	 * metoda cautare dupa combinatie id 
	 * @param orderID - order id
	 * @param clientID - client id
	 * @return - createorder
	 */
	public static CreateOrder findById(int orderID, int clientID)
	{
		CreateOrder toReturn = null;
		
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try {
			findStatement = dbConnection.prepareStatement(findStatementStringID);
			findStatement.setLong(1, orderID);
			findStatement.setLong(2,clientID);
			rs = findStatement.executeQuery();
			rs.next();

			float price= rs.getFloat("totalPrice");
			toReturn = new CreateOrder(orderID, clientID,price);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING,"CreateOrderDAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close((com.mysql.jdbc.Connection) dbConnection);
		}
		
		return toReturn;
	}
	
	
	/**
	 * metoda de inserare
	 * @param c - createorder de inserat
	 * @return - lista de id
	 */
	public static List<Integer> inserCreateOrder(CreateOrder c)
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		List<Integer> insertedId = new ArrayList<Integer>();
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setLong(1,c.getOrderID());
			insertStatement.setInt(2, c.getClientID());
			insertStatement.setFloat(3, c.getTotalPrice());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			
			if (rs.next()) {
				insertedId.add(rs.getInt(1));
				insertedId.add(rs.getInt(2));
				
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "CreateOrderDAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close((com.mysql.jdbc.Connection) dbConnection);
		}
		return insertedId;
	}
	
	/**
	 * stergere create order
	 * @param c - ce vrem sa stergem
	 * @return - lista de id
	 */
	public static List<Integer> deleteCreateOrder(CreateOrder c)
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement deleteStatement = null;
		List<Integer> deletedID = new ArrayList<Integer>();
		try {
			deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
			deleteStatement.setLong(1,c.getOrderID());
			deleteStatement.setLong(2,c.getClientID());
			deleteStatement.executeUpdate();

			ResultSet rs = deleteStatement.getGeneratedKeys();
			if (rs.next()) {
				deletedID.add(rs.getInt(1));
				deletedID.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "CreateOrderDAO:delete " + e.getMessage());
		} finally {
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close((com.mysql.jdbc.Connection) dbConnection);
		}
		return deletedID;
	}
	
	/**
	 * metoda de actualizare create order
	 * @param o - ce vrem sa actualizam
	 * @return - id create order
	 */
	
	public static int updateCreateOrder(CreateOrder o)
	{
		CreateOrder toReturn = null;
		
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement update = null;
		ResultSet rs = null;
		int updated=-1;
		
		try {
			update = dbConnection.prepareStatement(updateStatement, Statement.RETURN_GENERATED_KEYS);
			update.setInt(1,o.getOrderID());
			update.setInt(2, o.getClientID());
			update.setFloat(3,o.getTotalPrice());	
			update.setInt(4,o.getOrderID());
			//update.executeUpdate();
			rs=update.getGeneratedKeys();
			if (rs.next()) {
				updated = rs.getInt(1);
			}
		} catch (SQLException e) { 
			LOGGER.log(Level.WARNING,"CreateOrderDAO:updateCreateOrder " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(update);
			ConnectionFactory.close((com.mysql.jdbc.Connection) dbConnection);
		}
		
		return updated;
	}
	
	public static List<CreateOrder> selectAll()
	 {
		 List<CreateOrder> orders= new ArrayList<CreateOrder>();
		 
		 	Connection dbConnection = ConnectionFactory.getConnection();

			PreparedStatement selectAllStatement = null;
			String findAllStatement= "SELECT * FROM test.createorder";
			
			try {
				selectAllStatement = dbConnection.prepareStatement(findAllStatement);
				ResultSet rs = selectAllStatement.executeQuery();
				
					if(rs.next())
					{
						do {
							
							CreateOrder aux= new CreateOrder();
							int id1= rs.getInt(1);
							int id2= rs.getInt(2);
							float price= rs.getFloat(3);
							orders.add(new CreateOrder(id1,id2,price));
						
						
					}while(rs.next());
					
					}
					
				
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "CreateOrderDAO: findAllCreateOrders " + e.getMessage());
			} finally {
				ConnectionFactory.close(selectAllStatement);
				ConnectionFactory.close((com.mysql.jdbc.Connection) dbConnection);
				
			}
			return orders;
			
	 }
	


}
