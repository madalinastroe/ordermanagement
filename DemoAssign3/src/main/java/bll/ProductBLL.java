package bll;

import java.util.List;

import dao.ClientDAO;
import dao.ProductDAO;
import model.Client;
import model.Product;

public class ProductBLL {
	
	public ProductBLL()
	{
		
	}
	
	public static Product findProductById(int productID)
	{
		Product p=ProductDAO.findById(productID);
		
		if(p==null)
		{
			//throw new NoSuchElementException("The client with id =" + clientID + " was not found!");
			System.out.println("The product with id =" + productID + " was not found!");
		}
		return p;
	}
	
	public static Product findProductByName(String name)
	{
		Product p=ProductDAO.findByName(name);
		
		if(p==null)
		{
			//throw new NoSuchElementException("The client with id =" + clientID + " was not found!");
			System.out.println("The product with name =" + name + " was not found!");
		}
		return p;
	}
	
	public static int updateProduct(Product p)
	{
		return ProductDAO.updateProduct(p);
	}
	
	public static String insertProduct(Product p)
	{
		return ProductDAO.insertProduct(p);
	}
	
	public static int deleteProduct(Product p)
	{
		return ProductDAO.deleteProduct(p);
	}
	
	public List<Product> selectAllProducts()
	{
		return ProductDAO.selectAllProducts();
	}

}
