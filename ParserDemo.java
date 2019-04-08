import java.util.*;
import java.io.*;

public class ParserDemo 
{
	public static void main(String[] args)
	{
		/*
		testing the connection and add
		mySQLConnector con = new mySQLConnector("root", "Halfhonesttea15");
        String str = con.retrieve(7);
        con.add("test", "this is a test", "testing testing 1 2 3");
        System.out.println(str);
		*/

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

		File f = new File("C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\text1.txt");

		
		try {sc = new Scanner(f);}
		catch(FileNotFoundException anyletter) {};
		
		Parser parser = new Parser(sc);
		
		parser.getTopTen(); // WARNING: is currently a System.out test statement
		*/
		
	}

}


