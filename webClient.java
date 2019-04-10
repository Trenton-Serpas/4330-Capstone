import java.io.IOException;
import java.net.*;
import java.util.*;

//TODO Implement a send method for uploading top ten words.
//This will involve setting up different message types, defined by the header of the message.
public class WebClient 
{
	Socket soc;
	Scanner socScan;
	
	//This is only for testing.  It will read a file instead of listening to a socket
	public WebClient(String filname)
	{
		//TODO test this stuff with local files
	}
	
	public WebClient(InetAddress in, int socketNum)
	{
		try {
			soc = new Socket(in, socketNum);  
			socScan = new Scanner(soc.getInputStream());
		} 
		
		catch (Exception e) {
			System.out.println("Error: unable to connect to Socket.");  
			socScan.close();
			return;
		}
	}
	
	public WebClient(String hostName, int socketNum)
	{
		try {
			soc = new Socket(hostName, socketNum);  
			socScan = new Scanner(soc.getInputStream());
		} 
	
		catch (Exception e) {
			System.out.println("Error: unable to connect to Socket.");  
			//socScan.close();
			return;
		}
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
