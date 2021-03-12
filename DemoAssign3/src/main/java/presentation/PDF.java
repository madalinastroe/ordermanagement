package presentation;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.lang.reflect.Field;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

import bll.ClientBLL;
import bll.CreateOrderBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import dao.CreateOrderDAO;
import model.Client;
import model.CreateOrder;
import model.Order;
import model.Product;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class PDF {
	
	
	/**
	 * metoda de creare header tabel
	 * @param objectName - tabel bd
	 * @param dimension - dimensiune
	 * @param table - tabel
	 */
	public void createHeaders(String objectName,int dimension, PdfPTable table)
	{
		ArrayList<String> headers= new ArrayList<String>();
		
		if(objectName.equals("client"))
		{
			//vom crea tabel pentru clienti
			for(Field field: Client.class.getDeclaredFields())
			{
				headers.add(field.getName());
			}
		}
		
		else if(objectName.equals("product"))
		{
			for(Field field: Product.class.getDeclaredFields())
			{
				headers.add(field.getName());
			}
		}
		
		else if(objectName.equals("order"))
		{
			for(Field field: Order.class.getDeclaredFields())
			{
				headers.add(field.getName());
			}
		}
		

		else if(objectName.equals("createorder"))
		{
			for(Field field: CreateOrder.class.getDeclaredFields())
			{
				headers.add(field.getName());
			}
		}
		
		if(dimension == 3)
		{
			//tabelul va avea 3 coloane
			Stream.of(headers.get(0),headers.get(1),headers.get(2)).forEach(columnTitle ->{
				 PdfPCell header = new PdfPCell();
			       header.setBackgroundColor(new BaseColor(55,108,120));
			        header.setBorderWidth(1);
			        header.setPhrase(new Phrase(columnTitle));
			       header.setHorizontalAlignment(Element.ALIGN_CENTER);
			        table.addCell(header);
			    });
			
		}
		
		if(dimension == 4)
		{
			Stream.of(headers.get(0),headers.get(1),headers.get(2),headers.get(3)).forEach(columnTitle ->{
				 PdfPCell header = new PdfPCell();
			        header.setBackgroundColor(new BaseColor(55,108,120));
			        header.setBorderWidth(1);
			        header.setPhrase(new Phrase(columnTitle));
			        header.setHorizontalAlignment(Element.ALIGN_CENTER);
			        table.addCell(header);
			    });
		}

	}
	
	/**
	 * adauga randuri in tabel
	 * @param <T> - tip tabel
	 * @param list - elemente
	 * @param dimension - dimensiune
	 * @param table - tabel
	 */
	public <T> void addRowsInTable(ArrayList<T> list, int dimension, PdfPTable table)
	{
		
		T aux;
		
		for(int i=0;i<list.size();i++)
		{
			aux=list.get(i); 
			for(Field field : list.get(i).getClass().getDeclaredFields()) {
				 field.setAccessible(true);
			 
			 try {
				Object value = field.get(aux);
				 PdfPCell header = new PdfPCell();
				 header.setBackgroundColor(new BaseColor(193,210,214));
				 header.setPhrase(new Phrase(value.toString()));
			     header.setHorizontalAlignment(Element.ALIGN_CENTER);
				 table.addCell(header);
			   //table.addCell(value.toString());
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			}
		}
	}
	
	/**
	 * creare tabel
	 * @param <T> - tip tabel
	 * @param name - nume
	 * @param size - dimensiune
	 * @param list - elemente
	 * @param nr - contor
	 */
	public <T> void createTable(String name, int size, ArrayList<T> list, int nr)
	{
		Document document = new Document();
		
		StringBuilder sb= new StringBuilder();
		
		sb.append(name + "_table"+ nr+ ".pdf");
		nr++;
		
try {
			
			PdfWriter.getInstance(document, new FileOutputStream(sb.toString()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		document.open();
		
		PdfPTable table = new PdfPTable(size);
		createHeaders(name,size, table);
		addRowsInTable(list, size, table);
		
		try {
			document.add(table);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document.close();
	}
	
	public void understockWarning( String name , String mesaj)
	{
		Document document = new Document();
		StringBuilder sb = new StringBuilder();
		sb.append("Warning " + name + ".pdf");
		try {
			PdfWriter.getInstance(document, new FileOutputStream(sb.toString()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			
			e.printStackTrace();
		}
		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 16,  new BaseColor(0, 128, 0));
	
		Chunk chunk = new Chunk(mesaj, font);
		
		try {
			document.add(chunk);
		} catch (DocumentException e) {
			
			e.printStackTrace();
		}
		document.close();
	}
	
	/**
	 * creare bill
	 * @param order - order pentru care cream bill in format pdf
	 */
	public void createBill(CreateOrder order)
	{
		Document document = new Document();
		StringBuilder sb = new StringBuilder();
		StringBuilder clientName= new StringBuilder();
		StringBuilder total = new StringBuilder();
		StringBuilder product = new StringBuilder();
		sb.append("Bill " + order.getOrderID() + ".pdf");
		try {
			PdfWriter.getInstance(document, new FileOutputStream(sb.toString()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		document.open();
		
		Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
		Chunk chunk = new Chunk("Bill\n", font);
		
		Client c = ClientBLL.findClientById(order.getClientID());
		clientName.append("Client: " + c.getName() + "\n\n");
		
		Chunk chunk1= new Chunk(clientName.toString(), font);
		
		
		Paragraph par = new Paragraph();
		
		par.add(chunk);
		par.add(chunk1);
		
		ArrayList<CreateOrder> list= new ArrayList<CreateOrder>();
		CreateOrderDAO co= new CreateOrderDAO();
		list=(ArrayList<CreateOrder>) CreateOrderBLL.selectAll();
		System.out.println("********"+list);
		
		Order o= new Order();
		o=OrderBLL.findOrderById(order.getOrderID());
		
		
		total.append("\nTotal: " + order.getTotalPrice() + "  lei");
		par.add(new Chunk(total.toString(), font));
	
		try {
			document.add(par);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		document.close();
	
		
	}
}
