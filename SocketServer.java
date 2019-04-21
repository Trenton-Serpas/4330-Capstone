import java.net.Socket;
import java.util.Scanner;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.http.HttpServlet;

public class SocketServer extends HttpServlet 
{
	Socket cli;
	Scanner in;
	PrintWriter out;
	ServerSocket soc;
	
	MySQLConnector mc; 
	//This is hard coded for now
	
	public void waitForInput() throws InterruptedException
	{
		while(!in.hasNext())
		{
			Thread.sleep(1000);
		}
		String temp = in.nextLine();
		
		String[] input;
		
		
		switch (temp)
		{
		case "reqTitles":
			break;
		case "insertdoc":
			input = readDocument();
			Parser p = new Parser(new Scanner(input[1]));
			String[] topTen = p.getTopTen();
			insertDocument(input[0], input[1], topTen);
			break;
		}
	}
	
	
	public String[] readDocument()
	{		
		String ret[] = new String[2];
		
		ret[0] = in.nextLine();
		ret[1] = "";
		
		String temp;
		while((temp = in.nextLine()) != "abcd")
		{
			ret[1] += temp + "\n";
		}
		
		return ret;
	}
	
	public void insertDocument(String title, String content, String[] keywords)
	{
		mc.add(title, content, keywords);
	}
	
	public String[] retriveTopTen(String title)
	{  //Considered doing this in one statement, but it's messy.  If there are two works with the same title, this only returns the first
		String temp = mc.retrieve("select keyword1, keyword2, keyword3, keyword4, keyword5, keyword6, keyword7, keyword8, keyword9, keyword10 from documents where title = \"" + title + "\";");
		return temp.split("\n", -1)[1].split("\\0174{2}", -1); //remove column names, second split patter is 2 copies of octal value 174 aka |
	}
	
	public void close()
	{
		in.close();
		out.close();
		try { 
			cli.close();
			soc.close();
			mc.close();
		} catch (IOException e) { System.out.println("Error: cannot close web socket."); }
	}
	
	
	public void initWeb()
	{
		try {
			cli = soc.accept();
			in = new Scanner(cli.getInputStream());
			out = new PrintWriter(cli.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}

		
		System.out.println("Ready and waitingf");
		while(true)
		{
			if(in.hasNext())
				System.out.print(in.nextLine());
			//Thread.sleep(1000);
		}
		
		
	}
}
