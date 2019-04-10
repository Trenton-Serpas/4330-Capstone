import java.util.*;
import java.io.*;
import java.sql.SQLException;

public class ParserDemo 
{
	public static MySQLConnector msdbc;
	public static WebClient wc;
	
	
	public static void main(String[] args)
	{
		//Currently main simply creates and closes a MySQL and web connection.  Below is code I used for testing.
		msdbc = new MySQLConnector("Andrew", "cbasfish", "test");
		
		wc = new WebClient("127.0.0.1", 80); //Note: this only works if there's an active server on the port you specify
		
		
		/*//For testing this.retrieveTopTen.  Requires a database with entries.
		String[] sa = retriveTopTen("TLOTR 1");
		for(int i = 0; i < sa.length; i++)
			System.out.println(sa[i]);
		*/
		
		//For Testing mySqlConnector.retrieve.  
		//String temp = msdbc.retrieve("Select * from documents;");
		//System.out.println(temp);
		
		
		/*//Add garbage data
		String[] keywords = new String[10];
		keywords[0] = "asdfasdfasd";
		keywords[1] = "the";
		keywords[2] = "blaasdfh";
		keywords[3] = "a";
		keywords[4] = "a";
		keywords[5] = "a";
		keywords[6] = "a";
		keywords[7] = "o";
		keywords[8] = "sd";
		keywords[9] = "ksd";
		if(msdbc.add("TLOTR 1", "This is the body of the text.", keywords))
			System.out.println("It worked!!!");
			*/		
		
		/*  //Read files directly
		try {
			FileReader fr = new FileReader("C:\\Users\\Trent\\Desktop");
			BufferedReader br = new BufferedReader(fr);
		}
		*/
		
		msdbc.close();		
		wc.close();
	}

	
	public static void insertMySQL(String title, String content, String[] keywords)
	{
		msdbc.add(title, content, keywords);
	}
	
	public static String[] retriveTopTen(String title)
	{  //Considered doing this in one statement, but it's messy.  If there are two works with the same title, this only returns the first
		String temp = msdbc.retrieve("select keyword1, keyword2, keyword3, keyword4, keyword5, keyword6, keyword7, keyword8, keyword9, keyword10 from documents where title = \"" + title + "\";");
		return temp.split("\n", -1)[1].split("\\0174{2}", -1); //remove column names, second split patter is 2 copies of octal value 174 aka |
	}
}


