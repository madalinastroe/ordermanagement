package bll;

import java.util.List;

import dao.ClientDAO;
import dao.OrderDAO;
import model.Client;
import model.Order;

public class OrderBLL {
	
	/*
	 * Constructorul clasei OrderBLL
	 */
	public OrderBLL()
	{
		
	}
	
	public static Order findOrderById(int orderID)
	{
		Order c=OrderDAO.findById(orderID);
		
		if(c==null)
		{
			//throw new NoSuchElementException("The client with id =" + clientID + " was not found!");
			System.out.println("The order with id =" + orderID + " was not found!");
		}
		return c;
	}
	
	public static Order findOrderByName(String name)
	{
			Order c=OrderDAO.findByName(name);
		
		if(c==null)
		{
			//throw new NoSuchElementException("The client with id =" + clientID + " was not found!");
			System.out.println("The Order with name=" + name + " was not found!");
		}
		return c;
	}
	
	public int insertOrder(Order o)
	{
		return OrderDAO.insertOrder(o);
	}
	
	public static int deleteOrder(Order o)
	{
		return OrderDAO.deleteOrder(o);
	}
	
	public static int updateOrder(Order o)
	{
		return OrderDAO.updateOrder(o);
	}
		
	public static List<Order> selectAllOrders()
	{
		 return OrderDAO.selectAllOrders();
	}

	

}
