package bll;

import java.util.List;

import dao.CreateOrderDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import model.CreateOrder;
import model.Order;
import model.Product;

public class CreateOrderBLL {
	
	/**
	 * Constructorul clasei CreateOrderBLL
	 */
	public CreateOrderBLL()
	{
		
	}
	
	/**
	 * Metoda de cautare createOrder dupa id-ul clientului
	 * @param clientID - id-ul clientului dupa care se va face cautarea
	 * @return - un obiect de tip createOrder 
	 */
	public static CreateOrder findByClientID(int clientID)
	{
		CreateOrder c=CreateOrderDAO.findByClientID(clientID);
		if(c==null)
		{
			//throw new NoSuchElementException("The client with id =" + clientID + " was not found!");
			System.out.println("The order with client id= "+clientID+" was not found!");
		}
		return c;
	}
	
	/**
	 * Metoda de cautare a unui CreateOrder in functie de o combinatie de id-uri
	 * @param orderID - id-ul comenzii
	 * @param clientID - id-ul clientului
	 * @return - un obiect de tip createOrder
	 */
	public static CreateOrder findByID(int orderID, int clientID)
	{
		CreateOrder c=CreateOrderDAO.findById(orderID,clientID);
		
		if(c==null)
		{
			//throw new NoSuchElementException("The client with id =" + clientID + " was not found!");
			System.out.println("The order with order id =" + orderID + "and client id= "+clientID+" was not found!");
		}
		return c;
	}
	
	/**
	 * Metoda de inserare in baza de date a unui CreateOrder
	 */
	
	public List<Integer> insertCreateOrder(CreateOrder o)
	{
		return CreateOrderDAO.inserCreateOrder(o);
	}
	/*
	 * Metoda de stergere din baza de date a unui createOrder
	 */
	
	public static List<Integer> deleteCreateOrder(CreateOrder o)
	{
		return CreateOrderDAO.deleteCreateOrder(o);
	}
	
	/*
	 * Metoda de update in baza de date a unui CreateOrder
	 */
	
	public static int updateCreateOrder(CreateOrder c)
	{
		return CreateOrderDAO.updateCreateOrder(c);
	}
	
	/**
	 * MEtoda de selectare toate obiectele de tip CreateOrder din baza de date
	 * @return o lista cu toate obiectele
	 */
	public static List<CreateOrder> selectAll()
	{
		
		 return CreateOrderDAO.selectAll();
	}


}
