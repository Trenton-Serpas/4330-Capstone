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
		
		System.out.println(type + "make sure");
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
				createUser(request.getParameter("email"), request.getParameter("pass"));
	
				//update default titles
				response.getWriter().write(populateTitles(html2));
				break;
				
			case "login": // sends to signin
				
				//populate string
				String htmlL = populateHTML("signin.html");
				
				response.getWriter().write(htmlL);
				break;
				
			case "login complete"://sends to Documents
				
				//populate string
				String html3 = populateHTML("documents.html");
				
				//verify user
				boolean loginSuccess = login(request.getParameter("email"), request.getParameter("pass"));
				
				//update default titles
				response.getWriter().write(populateTitles(html3));
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
				response.getWriter().write(populateTitles(html5));
				break;
				
			case "documents": //sends to Documents
				
				//populate string
				String html6 = populateHTML("documents.html");
							
				//update default titles		
				response.getWriter().write(populateTitles(html6));
				break;
				
			case "open": // sends to output
				
				//populate string
				String html7 = populateHTML("output.html");
				
				//fetch title and text
				String body = getBody(request.getParameter("title"));
				
				html7 = html7.replaceAll("WORDSHERE", body);
				
				//output text		
				response.getWriter().write(html7);
				break;
				
			case "wordcloud": // sends to output
				
				//populate string
				String html8 = populateHTML("output.html");
				
				String[] keywords = getKeywords(request.getParameter("email"));
				
				String keywordString = "";
				
				for(int i = 0; i < keywords.length; i++)
				{
					keywordString += (keywords[i] + ", ");
				}
				
				html8 = html8.replaceAll("WORDSHERE", keywordString);
				
				response.getWriter().write(html8);
				break;
				
			case "search": // sends to search
				
				//populate string
				String html9 = populateHTML("search.html");
				
				response.getWriter().write(html9);
				break;
				
			case "search complete": // sends to documents
				
				//populate string
				String html10 = populateHTML("documents.html");
				
				ArrayList<String> validTitles = new ArrayList<String>();
				validTitles = search(request.getParameter("key1"), validTitles);
				validTitles = search(request.getParameter("key2"), validTitles);
				validTitles = search(request.getParameter("key3"), validTitles);
				validTitles = search(request.getParameter("key4"), validTitles);
				
				response.getWriter().write(populateTitles(html10));
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
				
				response.getWriter().write(populateTitles(html12));
				break;
				
			case "delete": // sends to documents
				
				//populate string
				String html13 = populateHTML("documents.html");
				
				deleteDoc(request.getParameter("title"));
				
				response.getWriter().write(populateTitles(html13));
				break;
		}
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
	
	private String populateTitles(String html)
	{
		String titlesHTML = "";
		List<String> titles = Arrays.asList(getTitles());
		
		for(int i = 0; i < titles.size(); i++)
		{
			titlesHTML += (titles.get(i) + ", ");
		}
		
		html.replaceAll("WORDSHERE", titlesHTML);
		
		return titlesHTML;
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
	
	private ArrayList<String> search(String key, ArrayList<String> validTitles)
	{
		List<String> titles = Arrays.asList(getTitles());//getTitles();
		for(int i = 0; i < titles.size(); i++)
		{
			String[] keywords = getKeywords(titles.get(i));
			
			for(int j = 0; j < keywords.length; j++)
			{
				if(keywords[j] == key)
				{
					validTitles.add(titles.get(i));
					break;
				}
			}
		}
		
		return validTitles;
	}
	public void deleteDoc(String title)
	{
		mc.retrieve("delete from documents where title = " + title + ";");
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
		String temp = mc.retrieve("select keyword1, keyword2, keyword3, keyword4, keyword5, keyword6, keyword7, keyword8, keyword9, keyword10 from documents where title = \"" + title + "\";");
		return temp.split("\n", -1)[1].split("\\0174{2}", -1); //remove column names, second split patter is 2 copies of octal value 174 aka |
	}
	
	public String[] getTitles()
	{
		String temp[] = mc.retrieve("select title from documents;").split("\n", -1);
		return Arrays.copyOfRange(temp, 1, temp.length);
	}
	
	public String getBody(String title)
	{
		return mc.retrieve("Select content from documents where title = \"" + title + "\";");
	}
	
	public void createUser(String email, String pass)
	{
		mc.addUser(email, pass);
	}
	
	public void deleteUser(String email)
	{
		mc.retrieve("delete from users where email = \"" + email + "\";" );
	}
	
	public boolean login(String email, String pass)
	{
		String[] temp = mc.retrieve("select email, pass from users where email = \"" + email + "\" and pass = \"" + pass + "\";").split("\n", -1);
		if(temp.length > 1)
			return true;
		return false;
		
	}
}
