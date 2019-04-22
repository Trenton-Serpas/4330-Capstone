import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MasterServlet")
public class MasterServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	MySQLConnector mc;
	
	//This path and the MC login info is hardcoded
	String fPath = "C:\\Users\\andre\\git\\4330Capstone\\html\\";
	
	public void init() throws ServletException 
	{
        mc = new MySQLConnector("Andrew", "cbasfish", "test");
		//System.out.println(mc.retrieve("Select * from documents;"));
	}
	
	
    public MasterServlet() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String type = request.getParameter("type"); // this can also be done using different servlets
		
		System.out.println(type);
		//String text = new String(Files.readAllBytes(Paths.get(fPath + "signin.html")), StandardCharsets.UTF_8);
		//response.getWriter().write(text);
		
		//System.out.println(mc.retrieve("select title from documents;"));
		
		
		//System.out.println(getKeywords("The Hobbit"));
		
		if(type == null)
			return;

		switch(type)
		{
			case "index": // sends to index
				response.getWriter().write(populateHTML("index.html"));
				break;
				
			case "create": // sends to signup
				//populate string
				response.getWriter().write(populateHTML("signup.html"));
				break;
			
			case "create complete"://sends to Documents
				
				//populate string
				String html2 = populateHTML("documents.html");
				
				//create user
				createUser(request.getParameter("email"), request.getParameter("pass"), request.getParameter("firstname"), request.getParameter("lastname"));
	
				//update default titles
				response.getWriter().write(populateTitles(html2, getTitles()));
				break;
				
			case "login": // sends to signin
				
				//populate string
				String htmlL = populateHTML("signin.html");
				
				response.getWriter().write(htmlL);
				break;
				
			case "login complete"://sends to Documents
				
				//populate string
				String html3 = populateHTML("loginauth.html");
				
				//verify user
				if(login(request.getParameter("email"), request.getParameter("pass")))
					response.getWriter().write(html3.replaceAll("BODYHERE", "Successfully Logged in!"));
				else
					response.getWriter().write(html3.replaceAll("BODYHERE", "You're not in the system!"));
				break;
				
			case "email": // sends to passwordemail.html
				
				//populate string
				String html4 = populateHTML("passwordemail.html");
				
				response.getWriter().write(html4);
				break;
				
			case "email complete": //sends to Documents
				
				//populate string
				String html5 = populateHTML("documents.html");
	
				//update default titles	
				response.getWriter().write(populateTitles(html5, getTitles()));
				break;
				
			case "documents": //sends to Documents
				
				//populate string
				String html6 = populateHTML("documents.html");
							
				//update default titles		
				response.getWriter().write(populateTitles(html6, getTitles()));
				break;
				
			case "open": // sends to output
				
				//populate string
				String html7 = populateHTML("open.html");
				
				//fetch title and text
				String body = getBody(request.getParameter("title"));
				
				html7 = html7.replaceAll("BODYHERE", body);
				
				//output text		
				response.getWriter().write(html7);
				break;
				
			case "wordcloud": // sends to output
				
				//populate string
				String html8 = populateHTML("wordcloud.html");
				
				String[] keywords = getKeywords(request.getParameter("title"));
				
				response.getWriter().write(populateTitles(html8, keywords));
				break;
				
			case "search": // sends to search
				
				//populate string
				String html9 = populateHTML("search.html");
				
				response.getWriter().write(html9);
				break;
				
			case "search complete": // sends to documents
				
				//populate string
				String html10 = populateHTML("documents.html");
				
				String[] keys = new String[]{request.getParameter("key1"), request.getParameter("key2"), request.getParameter("key3"), request.getParameter("key4")};		
				
				response.getWriter().write(populateTitles(html10, search(keys)));
				break;
				
			case "upload": // sends to add
				
				//populate string
				String html11 = populateHTML("add.html");
				
				response.getWriter().write(html11);
				break;
				
			case "upload complete": // sends to documents
				
				//populate string
				String html12 = populateHTML("documents.html");
				
				uploadDoc(request.getParameter("title"), request.getParameter("body"));
				
				response.getWriter().write(populateTitles(html12, getTitles()));
				break;
				
			case "delete": // sends to documents
				
				//populate string
				String html13 = populateHTML("documents.html");
				
				deleteDoc(request.getParameter("title"));
				
				response.getWriter().write(populateTitles(html13, getTitles()));
				break;
		}
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
	
	private String populateTitles(String html, String[] data)
	{
		//System.out.println("ok there's this much data " + data.length);
		if(data == null || data.length == 0)
		{
			//System.out.println("we out!");
			return html.replaceAll("WORDSHERE", "");
		}
		else if(data.length == 1)
		{
			//System.out.println(data[0]);
			return html.replaceAll("WORDSHERE", "\""+data[0]+"\"");
		}

		//System.out.println("data3 is \"" + data[2] + "\"");
		
		String temp = "";
		int i;
		
		for(i = 0; i <= data.length - 2; i++)
			temp += "\"" + data[i] + "\", ";
		
		temp += "\"" + data[i] + "\"";
		//System.out.println(titlesHTML);
		return html.replaceAll("WORDSHERE", temp);
	}

	private String populateHTML(String fName)
	{
		Scanner sc; // we will scan the html docs in each case and edit them before printing it using response writer
		String html = "";
		try
		{
			html = new String(Files.readAllBytes(Paths.get(fPath + fName)), StandardCharsets.UTF_8);
		}
		catch(Exception exe) 
		{
			System.out.println("Couldn't open the file");
		}
				
		return html;
	}
	
	private String[] search(String[] keys)
	{
		ArrayList<String> retVal = new ArrayList<String>();
		String[] titles = getTitles();//getTitles();
		if(titles == null || titles.length < 1)
			return null;
		for(int i = 0; i < titles.length; i++)
		{
			String[] keywords = getKeywords(titles[i]);
			
			for(int j = 0; j < keywords.length; j++)
			{
				if(keywords[j].compareTo(keys[0]) == 0 || 
						keywords[j].compareTo(keys[1]) == 0 ||
						keywords[j].compareTo(keys[2]) == 0 || 
						keywords[j].compareTo(keys[3]) == 0)
				{
					retVal.add(titles[i]);
					break;
				}
			}
		}
		
		return retVal.toArray(new String[retVal.size()]);
	}
	public void deleteDoc(String title)
	{
		mc.update("delete from documents where title = \"" + title + "\";");
		//return initTitles.length - getTitles().length; 
	}
	public void uploadDoc(String title, String body)
	{
		String[] keywords = new String[10];
		Parser p = new Parser(new Scanner(body));
		
		keywords = p.getTopTen();
		mc.add(title, body, keywords);
	}
	
	public String[] getKeywords(String title)
	{
		String[] temp = mc.retrieve("select keyword1, keyword2, keyword3, keyword4, keyword5, keyword6, keyword7, keyword8, keyword9, keyword10 from documents where title = \"" + title + "\";").split("\n", -1);
		if (temp.length > 1)
		{
			return temp[1].split("\\0174{2}", 0);
		}
		return null; //remove column names, second split patter is 2 copies of octal value 174 aka |
	}
	
	public String[] getTitles()
	{
		String temp[] = mc.retrieve("select title from documents;").split("\n", 2);
		if(temp.length <= 1)
			return null;
		return temp[1].split("\n", 0);
	}
	
	public String getBody(String title)
	{
		String[] temp = mc.retrieve("Select content from documents where title = \"" + title + "\";").split("\n", 2);
		
		if(temp.length > 1)
			return temp[1];
		return "";	
	}
	
	public void createUser(String email, String pass, String name1, String name2)
	{
		mc.addUser(email, pass, name1, name2);
	}
	
	public void deleteUser(String email)
	{
		mc.update("delete from users where email = \"" + email + "\";" );
	}
	
	public boolean login(String email, String pass)
	{
		String[] temp = mc.retrieve("select email, pass from users where email = \"" + email + "\" and pass = \"" + pass + "\";").split("\n", 0);
		if(temp.length > 1)
			return true;
		return false;
		
	}
}
