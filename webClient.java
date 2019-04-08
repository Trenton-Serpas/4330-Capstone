
import java.io.IOException;
import java.net.*;
import java.util.*;


public class webClient 
{
	Socket soc;
	Scanner socScan;
	
	public webClient(int socketNum, InetAddress in)
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
	
	public webClient(int socketNum, String hostName)
	{
		try {
			soc = new Socket(hostName, socketNum);  
			socScan = new Scanner(soc.getInputStream());
		} 
	
		catch (Exception e) {
			System.out.println("Error: unable to connect to Socket.");  
			socScan.close();
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
			return;
		}
		
		
		Parser p = new Parser(new Scanner(input[1]));
		String[] topTen = p.getTopTen();
		
		

		
			
	
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
	
}
