package bll;

import java.util.ArrayList;
import java.util.List;

import connection.ConnectionFactory;
import model.Client;

import java.util.NoSuchElementException;

import dao.AbstractDAO;
import dao.ClientDAO;
import model.Client;

public class ClientBLL {
	
	/**
	 * Constructorul clasei ClientBLL
	 */
	
	public ClientBLL()
	{
		
	}
	
	/**
	 * Metoda de cautare a unui client in baza de date dupa id
	 * @param clientID id-ul clientului
	 * @return clientul din baza de date care are id-ul specificat
	 */
	public static Client findClientById(int clientID)
	{
		Client c=ClientDAO.findById(clientID);
		
		if(c==null)
		{
			//throw new NoSuchElementException("The client with id =" + clientID + " was not found!");
			System.out.println("The client with id =" + clientID + " was not found!");
		}
		return c;
	}
	/**
	 * Metoda de cautare in baza de date a unui client dupa nume
	 * @param name numele dupa care se va face cautarea clientului
	 * @return clientul din baza de date care are numele specificat
	 */
	
	public static Client findClientByName(String name)
	{
			Client c=ClientDAO.findByName(name);
		
		if(c==null)
		{
			//throw new NoSuchElementException("The client with id =" + clientID + " was not found!");
			System.out.println("The client with name=" + name + " was not found!");
		}
		return c;
	}
	
	/**
	 * Metoda de inserare client in baza de date, se apeleaza din ClientDAO
	 * @param client - clientul care se va introduce in baza de date
	 * @return - clientul care se va insera
	 */
	public int insertClient(Client client)
	{
		return ClientDAO.insertClient(client);
	}
	
	/**
	 * Metoda de stergere client din baza de date, se apeleaza din ClientDAO
	 * @param client - clientul care se va sterge in baza de date
	 * @return clientul care se va sterge
	 */
	
	public static int deleteClient(Client client)
	{
		return ClientDAO.deleteClient(client);
	}
	
	/**
	 * Metoda de selectare a toti clientilor din baza de date
	 * @return o lista cu toti clientii
	 */
	
	public List<Client> selectAllClients()
	{
		 return ClientDAO.selectAllClients();
	}
	
	
	

	
}
