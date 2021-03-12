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
import model.Client;
import model.CreateOrder;
import model.Order;
import model.Product;

public class OrderDAO {
	
	protected static final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());
	private static final String insertStatementString = "INSERT INTO test.order (orderID,product,quantity)"
			+ " VALUES (?,?,?)";
	private static final String deleteStatementString= "delete from test.order where orderID= ?";
	private final static String findStatementStringID = "SELECT * FROM test.order where orderID = ?";
	private final static String findStatementStringName = "SELECT * FROM test.order where product = ?";
	private final static String updateStatement = "update test.order set  orderID=? product=? quantity=?";

	/**
	 * cautare order dupa id
	 * @param orderID - id 
	 * @return - order cu id specificat
	 */
	
	public static Order findById(int orderID)
	{
		Order toReturn = null;
		
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try {
			findStatement = dbConnection.prepareStatement(findStatementStringID);
			findStatement.setLong(1, orderID);
			rs = findStatement.executeQuery();
			rs.next();

			String product = rs.getString("product");
			int quantity= rs.getInt("quantity");
			toReturn = new Order(orderID, product,quantity);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING,"OrderDAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close((com.mysql.jdbc.Connection) dbConnection);
		}
		
		return toReturn;
	}
	
	/**
	 * cautare dupa nume produs
	 * @param product - nume produs
	 * @return order
	 */
	public static Order findByName(String product)
	{
		Order toReturn = null;
		
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try {
			findStatement = dbConnection.prepareStatement(findStatementStringName);
			findStatement.setString(1, product);
			rs = findStatement.executeQuery();
			rs.next();
			int orderID= rs.getInt("orderID");
			int quantity=rs.getInt("quantity");
			toReturn = new Order(orderID, product,quantity);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING,"OrderDAO: findByName" + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close((com.mysql.jdbc.Connection) dbConnection);
		}
		
		return toReturn;
	}
	
	/**
	 * inserare order
	 * @param order - order de inserat
	 * @return - id order
	 */
	public static int insertOrder(Order order)
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setLong(1,order.getOrderID());
			insertStatement.setString(2, order.getProduct());
			insertStatement.setLong(3, order.getQuantity());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "OrderDAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close((com.mysql.jdbc.Connection) dbConnection);
		}
		return insertedId;
	}
	
	/**
	 * stergere order
	 * @param order - order de sters
	 * @return - id order 
	 */
	public static int deleteOrder(Order order)
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement deleteStatement = null;
		int deletedID = -1;
		try {
			deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
			deleteStatement.setLong(1,order.getOrderID());
			deleteStatement.executeUpdate();

			ResultSet rs = deleteStatement.getGeneratedKeys();
			if (rs.next()) {
				deletedID = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "OrderDAO:delete " + e.getMessage());
		} finally {
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close((com.mysql.jdbc.Connection) dbConnection);
		}
		return deletedID;
	}
	
	/**
	 * actualizare
	 * @param o - order de actualizat
	 * @return - id order de actualizat
	 */
	public static int updateOrder(Order o)
	{
		Order toReturn = null;
		
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement update = null;
		ResultSet rs = null;
		int updated=-1;
		
		try {
			update = dbConnection.prepareStatement(updateStatement, Statement.RETURN_GENERATED_KEYS);
			update.setInt(1,o.getOrderID());
			update.setString(2, o.getProduct());
			update.setFloat(3,o.getQuantity());			
			//update.executeUpdate();
			rs=update.getGeneratedKeys();
			if (rs.next()) {
				updated = rs.getInt(1);
			}
		} catch (SQLException e) { 
			LOGGER.log(Level.WARNING,"OrderDAO:updateOrder " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(update);
			ConnectionFactory.close((com.mysql.jdbc.Connection) dbConnection);
		}
		
		return updated;
	}

	/**
	 * metoda selectie totala
	 * @return lista cu toate de obiectele de tip order
	 */
	public static List<Order> selectAllOrders()
	 {
		 List<Order> orders= new ArrayList<Order>();
		 
		 	Connection dbConnection = ConnectionFactory.getConnection();

			PreparedStatement selectAllStatement = null;
			String findAllStatement= "SELECT * FROM test.order";
			
			try {
				selectAllStatement = dbConnection.prepareStatement(findAllStatement);
				ResultSet rs = selectAllStatement.executeQuery();
				
					if(rs.next())
					{
						do {
							
							Order aux= new Order();
							int id= rs.getInt(1);
							String product=rs.getString(2);
							int quantity= rs.getInt(3);
							orders.add(new Order(id,product,quantity));
						
						
					}while(rs.next());
					
					}
					
				
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "OrderDAO:findAllOrders " + e.getMessage());
			} finally {
				ConnectionFactory.close(selectAllStatement);
				ConnectionFactory.close((com.mysql.jdbc.Connection) dbConnection);
				
			}
			return orders;
			
	 }


}
