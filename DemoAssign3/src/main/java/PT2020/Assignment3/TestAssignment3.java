package PT2020.Assignment3;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;

import bll.ClientBLL;
import bll.CreateOrderBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import model.Client;
import model.CreateOrder;
import model.Order;
import model.Product;
import presentation.FileParser;

/**
 * Hello world!
 *
 */
public class TestAssignment3 
{
    public static void main(String[] args)
    {	
     	int contor=0;

		String file=args[0];
		FileParser f=new FileParser(file);
		ArrayList<String> lines=f.getLines(file);
		System.out.println("-----------------------");
		ArrayList<ArrayList<String>> aux=f.createCommands(lines);
		f.execute(aux);

    }
}
