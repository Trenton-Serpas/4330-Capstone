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

	int count;
	MySQLConnector mc;
	String fPath = "C:\\Users\\andre\\git\\4330Capstone\\html\\";
	
	public void init() throws ServletException 
	{
        mc = new MySQLConnector("Andrew", "cbasfish", "test");
        count = 3;
		File f = new File(fPath + "Test"+count+".txt");
		System.out.println(mc.retrieve("Select * from documents;"));
		try {
		if(f.createNewFile())
		{
			f.setWritable(true);
			FileWriter fw = new FileWriter(f);
			fw.write("uh plz work lol" +count);
			fw.close();
			return;
		}}
		catch(Exception e)
		{}
	}
	
	
    public MasterServlet() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String type = request.getParameter("type"); // this can also be done using different servlets
		Scanner sc; // we will scan the html docs in each case and edit them before printing it using response writer
		
		System.out.println(type);
		
		String text = new String(Files.readAllBytes(Paths.get(fPath + "signin.html")), StandardCharsets.UTF_8);

		response.getWriter().write(text);
		
		return;

		/*
		switch(type)
		{
		case "create":
		
		case "create complete"://sends to Documents
			
			sc = new Scanner(new File("../HTML/documents.html"));
			
			//create user
			//boolean creationSuccess = createUser(request.getParameter("email"), request.getParameter("pass"));
			
			//fetch titles
			//List<String> titlesC = getTitles();
			
			//update default titles
			
			
			response.getWriter().write("");
			
		case "login":
			
		case "login complete"://sends to Documents
			
			sc = new Scanner(new File("../HTML/documents.html"));
			
			//verify user
			//boolean loginSuccess = login(request.getParameter("email"), request.getParameter("pass"));
			
			//fetch titles
			List<String> titlesLC = getTitles();
			
			//update default titles
			
		case "email":
			
			
			
		case "email complete": //sends to Documents
			
			sc = new Scanner(new File("../HTML/documents.html"));
			
			//fetch titles
			List<String> titlesEC = getTitles();
			
			//update default titles
			
			
		case "documents": //sends to Documents
			
			sc = new Scanner(new File("../HTML/documents.html"));
			
			//fetch titles
			List<String> titlesD = getTitles();
			
			//update default titles
			
			
		case "open": // sends to output
			
			sc = new Scanner(new File("../HTML/documents.html"));
			
			//fetch titles
			String titleAndFullbody = getTitleAndFullbody();
			
			//update default titles			
			
		case "wordcloud": // sends to output
			
		case "search": // sends to search
			
		case "search complete": // sends to documents
			
		case "upload": // sends to upload
			
		case "upload complete": // sends to documents
			
		case "delete": // sends to documents
			
		}*/
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
