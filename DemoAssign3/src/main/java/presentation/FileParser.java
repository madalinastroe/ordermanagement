package presentation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;

import bll.ClientBLL;
import bll.CreateOrderBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import dao.CreateOrderDAO;
import model.Client;
import model.CreateOrder;
import model.Order;
import model.Product;

public class FileParser {
	
	private String file;
	
	/**
	 * constructor clasa 
	 * @param file - fisierul din care citim comenzile
	 */
	
	public FileParser(String file) {
		super();
		this.file = file;
	}

	/**
	 * metoda de separare cuvinte pe linie
	 * @param line - linia 
	 * @return - arraylist de cuvinte
	 */
	public static ArrayList<String> getWords(String line)
	{
		//separam cuvintele de pe o linie
		ArrayList<String> words=new ArrayList<String>();
		
		Pattern pattern= Pattern.compile("\\s*(\\s|:|,)\\s*"); //regular expression
		String[] separatedWords= pattern.split(line); //cuvintele de pe linie au fost separate 
		for(String s: separatedWords)
		{
			words.add(s);
		}
		
		
		return words;
		
	}
	
	/**
	 * metoda creare comenzi
	 * @param lines - arraylist de linii
	 * @return comenzile de pe toate liniile
	 */
	
	public ArrayList<ArrayList<String>> createCommands(ArrayList<String> lines)
	{
		ArrayList<ArrayList<String>> commands= new ArrayList<ArrayList<String>>();
		int i=0;
		for(String line:lines)
		{
			ArrayList<String> aux=getWords(lines.get(i));
			commands.add(aux);
			i++;
			
		}
		
		return commands;
	}
	
	/**
	 * metoda obtinere linii
	 * @param file - fisier
	 * @return - lista de linii
	 */
	public ArrayList<String> getLines(String file)
	{
		ArrayList<String> lines=new ArrayList<String>();
		try {
			FileInputStream fis=new FileInputStream(file);
			Scanner scanner=new Scanner(fis);
			
			while(scanner.hasNextLine())
			{
				String linie=scanner.nextLine();
				lines.add(linie);
			}  
			
			return lines;
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return lines;
	}
	
	
	@Override
	public String toString() {
		return "FileParser [file=" + file + "]";
	}

	/**
	 * comanda inserare
	 * @param line - cuvinte
	 * @param id - id client
	 */
	public void insertClientCommand(ArrayList<String> line, int id) {
		ClientBLL client=new ClientBLL();
		StringBuilder name = new StringBuilder();
		name.append(line.get(2) + " " + line.get(3));
		Client c = new Client(id,name.toString(), line.get(4));
		client.insertClient(c);
	}
	
	/**
	 * comanda inserare 
	 * @param line - lista cuvinte
	 * @param id - id produs
	 */
	public void insertProductCommand(ArrayList<String> line, int id) {
		ProductBLL product=new ProductBLL();
		int quantity=Integer.parseInt(line.get(3));
		float price=Float.parseFloat(line.get(4));
		Product c=product.findProductByName(line.get(2).toString());
		
		if(c==null)
		{
			//daca nu exista produs cu numele asta
			c = new Product(id,line.get(2).toString(),quantity,price);
			product.insertProduct(c);
		}
		else
		{
			//daca exista produsul, ii facem update
			int newQuantity=c.getQuantity()+quantity;
			c=new Product(c.getProductID(),line.get(2),newQuantity,c.getPrice());
			product.updateProduct(c);		
			
		}
		
	}
	
	/**
	 * stergere client
	 * @param line - lista cuvinte
	 */
	public void deleteClientCommand(ArrayList<String> line)
	{
		ClientBLL client=new ClientBLL();
		StringBuilder name = new StringBuilder();
		name.append(line.get(2) + " " + line.get(3));
		Client c=client.findClientByName(name.toString());
		client.deleteClient(c);
	}

	public void deleteProductCommand(ArrayList<String> line)
	{
		ProductBLL product=new ProductBLL();
		Product c = product.findProductByName(line.get(2));
		product.deleteProduct(c);
	}
	
	/**
	 * creare order
 	 * @param line - lista cuvinte
	 * @param id - id order
	 */
	public void createOrder(ArrayList<String> line, int id)
	{
		ClientBLL client=new ClientBLL();
		ProductBLL product= new ProductBLL();
		OrderBLL order=new OrderBLL();
		CreateOrderBLL co= new CreateOrderBLL();
		CreateOrder o=new CreateOrder();
		Order od=new Order();
		
		float totalPrice=0;
		
		//Order: Luca George, apple, 5
		StringBuilder name=new StringBuilder();
		name.append(line.get(1)+" "+line.get(2));
		int quantity=Integer.parseInt(line.get(4));
		Client c=client.findClientByName(name.toString());
		if(c==null)
		{
			System.out.println("An error occured while searching for client.");
			PDF pdf= new PDF();
			String s="An error occured while searching for client.";
			pdf.understockWarning(c.getName(),s);
			
		}
		else
		{
			Product p=product.findProductByName(line.get(3));
			if(p==null)
			{
				System.out.println("An error occured while searching for product.");
				PDF pdf= new PDF();
				String s="An error occured while searching for product.";
				pdf.understockWarning(p.getName(),s);
			}
			else if(p.getQuantity()<quantity)
			{
				System.out.println("Not enough products");
				PDF pdf= new PDF();
				String s="";
				s+="We are sorry, "+c.getName()+", but ";
				s+="we dont have enough products ("+p.getName()+").";
				
				pdf.understockWarning(p.getName(),s);
			}
			else
			{
				//inseamna ca avem produse 
				int newQuantity=p.getQuantity()-quantity;
				totalPrice=(float)(quantity*p.getPrice());
				System.out.println("******************* "+totalPrice);
				o=co.findByClientID(c.getClientID());
				
				o=new CreateOrder(id,c.getClientID(),totalPrice);
				co.insertCreateOrder(o);
				/*
				
				if(o==null)
				{
					//nu exista order
					o=new CreateOrder(id,c.getClientID(),totalPrice);
					co.insertCreateOrder(o);
					
				}
				else
				{
					//comanda exista, deci actualizam
					o.setTotalPrice(totalPrice+o.getTotalPrice());
					co.updateCreateOrder(o);
					
				}*/
				
				PDF pdf= new PDF();
				pdf.createBill(o);
				
				
				od=order.findOrderById(o.getOrderID());
				
				if(od==null)
				{
					od=new Order(o.getOrderID(),p.getName(),quantity);
					order.insertOrder(od);
				}
				else
				{
					od.setQuantity(od.getQuantity()+quantity);
					order.updateOrder(od);
				}
				Product pAux=new Product(p.getProductID(),p.getName(),newQuantity,p.getPrice());
				product.updateProduct(pAux);
				
			}
			
		}
				
	}
	
	/**
	 * report client
	 * @param contor - contor fisier pdf
	 */
	public void clientReport(int contor)
	{
		ClientBLL client= new ClientBLL();
    	List<Client> clients= new ArrayList<Client>();
    	clients=client.selectAllClients();
    	PDF generator= new PDF();
    	generator.createTable("client",3,(ArrayList<Client>)clients,contor);
    	
	}
	
	/**
	 * report product
	 * @param contor - contor fisier pdf
	 */
	public void productReport(int contor)
	{
		ProductBLL product= new ProductBLL();
		List<Product> products= new ArrayList<Product>();
		products=product.selectAllProducts();
		PDF generator= new PDF();
    	generator.createTable("product",4,(ArrayList<Product>) products,contor);
	}
	
	/**
	 * order report
	 * @param contor fisier pdf
	 */
	public void orderReport(int contor)
	{
		OrderBLL order= new OrderBLL();
		List<Order> orders= new ArrayList<Order>();
		orders=order.selectAllOrders();
		PDF generator= new PDF();
    	generator.createTable("order",3,(ArrayList<Order>) orders,contor);
	}
		
	/**
	 * report create order 
	 * @param contor contor fisier pdf
	 */
	public void createOrderReport(int contor)
	{
		CreateOrderBLL order= new CreateOrderBLL();
		List<CreateOrder> orders= new ArrayList<CreateOrder>();
		CreateOrderDAO od= new CreateOrderDAO();
		orders=order.selectAll();
		PDF generator= new PDF();
    	generator.createTable("createorder",3,(ArrayList<CreateOrder>) orders,contor);
		
	}
	
	/**
	 * metoda executie comenzi
	 * @param lines - lista de comenzi
	 */
	public void execute(ArrayList<ArrayList<String>> lines)
	{
		int counterClient=0,counterProduct=0, counterOrder=0;
		int cClient=0;
		int cProduct=0;
		int cOrder=0;
		int cCreate=0;
		
		for(ArrayList<String>line: lines)
		{
			for(int i=0;i<line.size();i++)
			{
				if(line.get(i).equals("Insert"))
				{
					//suntem pe ramura de insert
					if(line.get(i+1).equals("client"))
					{
						System.out.println("Inseram client: "+line.get(i+2));
						insertClientCommand(line,++counterClient);
					}
					else if(line.get(i+1).equals("product"))
					{
						System.out.println("Inseram produs: "+line.get(i+2));
						insertProductCommand(line,++counterProduct);
					}
				}
				else if(line.get(i).equals("Delete"))
				{
					if(line.get(i+1).equals("client"))
					{
						System.out.println("Stergem client: "+line.get(i+2));
						deleteClientCommand(line);
					}
					else if(line.get(i+1).equals("product"))
					{
						System.out.println("Stergem produs: "+line.get(i+2));
						deleteProductCommand(line);
					}
				}
				else if(line.get(i).equals("Order"))
				{
					System.out.println("Cream comanda: "+line.get(i+2));
					createOrder(line,++counterOrder);
				}
				
				
				
				else if(line.get(i).equals("Report"))
				{
					if(line.get(i+1).equals("client"))
					{
						clientReport(++cClient);
					}
					else if(line.get(i+1).equals("product"))
					{
						productReport(++cProduct);
					}
					else if(line.get(i+1).equals("order"))
					{
						orderReport(++cOrder);
						createOrderReport(++cCreate);
					}
				}
			}
		}
	}
	
	
}
