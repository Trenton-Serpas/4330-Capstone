import java.util.*;
import java.io.*;

public class ParserDemo 
{
	public static void main(String[] args)
	{
		/*
		try {
			FileReader fr = new FileReader("C:\\Users\\Trent\\Desktop");
			BufferedReader br = new BufferedReader(fr);
		}

		*/
		
		mySQLConnector msdbc = new mySQLConnector("Andrew", "cbasfish");
		
		msdbc.retrieve(3);
		
		
		
		
		
		/*
		Scanner sc = null;
		
		File f = new File("C:\\Users\\andre\\Desktop\\a.txt");
		
		try {sc = new Scanner(f);}
		catch(FileNotFoundException anyletter) {};
		
		Parser parser = new Parser(sc);
		
		parser.getTopTen(); // WARNING: is currently a System.out test statement
		*/
		
	}

}


