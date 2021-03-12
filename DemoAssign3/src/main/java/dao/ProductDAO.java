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
import model.Product;

public class ProductDAO {
	
	protected static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());
	private static final String insertStatementString = "INSERT INTO product (productID,name,quantity,price)"
			+ " VALUES (?,?,?,?)";
	private static final String deleteStatementString= "delete from product where productID= ?";
	private final static String findStatementStringID = "SELECT * FROM product where productID = ?";
	private final static String findStatementStringName = "SELECT * FROM product where name = ?";
	private final static String updateStatement = "update product set  productID=?, name=?, quantity=?, price=? where productID=?";

	
	public static Product findById(int productID)
	{
		Product toReturn = null;
		
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try {
			findStatement = dbConnection.prepareStatement(findStatementStringID);
			findStatement.setLong(1, productID);
			rs = findStatement.executeQuery();
			rs.next();

			String name = rs.getString("name");
			int quantity=rs.getInt("quantity");
			float price=rs.getFloat("price");
			toReturn = new Product(productID,name,quantity,price);
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
	 * metoda inserare produs
	 * @param product - produs de inserat
	 * @return - id produs de inserat
	 */
	public static String insertProduct(Product product)
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		String name="";
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setLong(1,product.getProductID());
			insertStatement.setString(2, product.getName());
			insertStatement.setInt(3, product.getQuantity());
			insertStatement.setFloat(4,product.getPrice());
			
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				//insertedId = rs.getInt(1);
				name=rs.getString(2);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProductDAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close((com.mysql.jdbc.Connection) dbConnection);
		}
		return name;
	}
	
	/**
	 * actualizare produs
	 * @param product - produs de actualizat
	 * @return id produs
	 */
	public static int updateProduct(Product product)
	{
		Product toReturn = null;
		
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement update = null;
		ResultSet rs = null;
		int updated=-1;
		
		try {
			update = dbConnection.prepareStatement(updateStatement, Statement.RETURN_GENERATED_KEYS);
			update.setInt(1,product.getProductID());
			update.setString(2, product.getName());
			update.setInt(3,product.getQuantity());
			update.setFloat(4,product.getPrice());
			update.setInt(5,product.getProductID());
			
			update.executeUpdate();
			rs=update.getGeneratedKeys();
			if (rs.next()) {
				updated = rs.getInt(1);
			}
		} catch (SQLException e) { 
			LOGGER.log(Level.WARNING,"ProductDAO:updateProduct " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(update);
			ConnectionFactory.close((com.mysql.jdbc.Connection) dbConnection);
		}
		
		return updated;
	}
	
	
	public static Product findByName(String name)
	{
		Product toReturn = null;
		
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try {
			findStatement = dbConnection.prepareStatement(findStatementStringName);
			findStatement.setString(1, name);
			rs = findStatement.executeQuery();
			rs.next();
			int productID= rs.getInt("productId");
			int quantity=rs.getInt("quantity");
			float price=rs.getFloat("price");
			toReturn = new Product(productID,name,quantity,price );
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING,"ProductDAO:findProductByName " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close((com.mysql.jdbc.Connection) dbConnection);
		}
		
		return toReturn;
	}
	
	/**
	 * metoda stergere produs
	 * @param product - produs de sters
	 * @return id produs de sters
	 */
	public static int deleteProduct(Product product)
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement deleteStatement = null;
		int deletedID = -1;
		try {
			deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
			deleteStatement.setLong(1,product.getProductID());
			deleteStatement.executeUpdate();

			ResultSet rs = deleteStatement.getGeneratedKeys();
			if (rs.next()) {
				deletedID = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProductDAO:delete " + e.getMessage());
		} finally {
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close((com.mysql.jdbc.Connection) dbConnection);
		}
		return deletedID;
	}
	
	/**
	 * selectie totala
	 * @return lista cu produse din baza de date
	 */
	public static List<Product> selectAllProducts()
	 {
		 List<Product> products= new ArrayList<Product>();
		 
		 	Connection dbConnection = ConnectionFactory.getConnection();

			PreparedStatement selectAllStatement = null;
			String findAllStatement= "SELECT * FROM test.product";
			
			try {
				selectAllStatement = dbConnection.prepareStatement(findAllStatement);
				ResultSet rs = selectAllStatement.executeQuery();
				
					if(rs.next())
					{
						do {
							
							Product aux= new Product();
							int id= rs.getInt(1);
							String name=rs.getString(2);
							int quantity= rs.getInt(3);
							float price=rs.getFloat(4);
							
							products.add(new Product(id,name,quantity,price));
						
						
					}while(rs.next());
					
					}
					
				
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "ClientDAO:findAllClients " + e.getMessage());
			} finally {
				ConnectionFactory.close(selectAllStatement);
				ConnectionFactory.close((com.mysql.jdbc.Connection) dbConnection);
				
			}
			return products;
			
	 }

	
}
