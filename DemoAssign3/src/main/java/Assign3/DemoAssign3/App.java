package Assign3.DemoAssign3;

import java.util.ArrayList;

import presentation.FileParser;

public class App 
{
    
    	public static void main(String[] args)
        {	
         	int contor=0;

    		String file="commands.txt";
    		FileParser f=new FileParser(file);
    		ArrayList<String> lines=f.getLines(file);
    		System.out.println("-----------------------");
    		ArrayList<ArrayList<String>> aux=f.createCommands(lines);
    		f.execute(aux);
        	
    }
}
