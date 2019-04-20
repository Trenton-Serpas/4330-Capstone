import java.io.IOException;
import java.net.*;
import java.util.*;

//TODO Implement a send method for uploading top ten words.
//This will involve setting up different message types, defined by the header of the message.
public class WebClient 
{
	Socket soc;
	public static MySQLConnector msdbc;
	Scanner socScan;
	
	public static void main(String[] args)
	{
		//Currently main simply creates and closes a MySQL and web connection.  Below is code I used for testing.
		msdbc = new MySQLConnector("Andrew", "cbasfish", "test");
		
		//Note: this only works if there's an active server on the port you specify
		try {
			soc = new Socket("127.0.0.1", 80);  
			socScan = new Scanner(soc.getInputStream());
		} 
		
		catch (Exception e) {
			System.out.println("Error: unable to connect to Socket.");  
			socScan.close();
			return;
		}
		
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
		close();
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
	
	//This is only for testing.  It will read a file instead of listening to a socket
	public WebClient(String filname)
	{
		//TODO test this stuff with local files
	}
	
	//TODO think about infinite runtime
	public void waitForInput() throws InterruptedException
	{
		while(!socScan.hasNext())
		{
			Thread.sleep(1000);
		}
		
		String[] input = readInput();
	
		if(input[0] == null && input[1] == null)
		{
			System.out.println("Error: Unable to read input");
		}
		else 
		{
			Parser p = new Parser(new Scanner(input[1]));
			String[] topTen = p.getTopTen();
			
			ParserDemo.insertMySQL(input[0], input[1], topTen);
		}
	}
	
	
	public String[] readInput()
	{
		if(socScan.nextLine() != "abcd")
		{
			System.out.println("Error: Message doesn't start with ABCD");
			return null;
		}
		
		String ret[] = new String[2];
		
		ret[0] = socScan.nextLine();
		ret[1] = "";
		
		String temp;
		while((temp = socScan.nextLine()) != "abcd")
		{
			ret[1] += temp + "\n";
		}
		
		return ret;
	}
	
	public void close()
	{
		socScan.close();
		try { soc.close(); } catch (IOException e) { System.out.println("Error: cannot close web socket."); }
	}
}
