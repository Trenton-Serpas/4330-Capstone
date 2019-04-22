
import java.util.*;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MasterServlet")
public class MasterServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String type = request.getParameter("type"); // this can also be done using different servlets
		
		switch(type)
		{
		case "create": // sends to signup
			
			//populate string
			String html1 = populateHTML("../HTML/signup.html");
			
			response.getWriter().write(html1);
			
		case "create complete"://sends to Documents
			
			//populate string
			String html2 = populateHTML("../HTML/documents.html");
			
			//create user
			boolean creationSuccess = createUser(request.getParameter("email"), request.getParameter("pass"));
			
			//fetch titles
			List<String> titlesC = getTitles();
			
			//update default titles
			
			
			response.getWriter().write(html2);
			
		case "login": // sends to signin
			
			//populate string
			String htmlL = populateHTML("../HTML/signin.html");
			
			response.getWriter().write(htmlL);
			
		case "login complete"://sends to Documents
			
			//populate string
			String html3 = populateHTML("../HTML/documents.html");
			
			//verify user
			boolean loginSuccess = login(request.getParameter("email"), request.getParameter("pass"));
			
			//fetch titles
			List<String> titlesLC = getTitles();
			
			//update default titles
			
			
			response.getWriter().write(html3);
			
		case "email": // sends to passwordemail.html
			
			//populate string
			String html4 = populateHTML("../HTML/passwordemail.html");
			
			response.getWriter().write(html4);
			
		case "email complete": //sends to Documents
			
			//populate string
			String html5 = populateHTML("../HTML/documents.html");
			
			//fetch titles
			List<String> titlesEC = getTitles();
			
			//update default titles
			
			
			response.getWriter().write(html5);
			
		case "documents": //sends to Documents
			
			//populate string
			String html6 = populateHTML("../HTML/documents.html");
			
			//fetch titles
			List<String> titlesD = getTitles();
			
			//update default titles
			
			
			response.getWriter().write(html6);
			
		case "open": // sends to output
			
			//populate string
			String html7 = populateHTML("../HTML/output.html");
			
			//fetch title and text
			String titleAndFullbody = getTitleAndFullbody();
			
			//output text		
			
			
			response.getWriter().write(html7);
			
		case "wordcloud": // sends to output
			
			//populate string
			String html8 = populateHTML("../HTML/output.html");
			
			response.getWriter().write(html8);
			
		case "search": // sends to search
			
			//populate string
			String html9 = populateHTML("../HTML/search.html");
			
			response.getWriter().write(html9);
			
		case "search complete": // sends to documents
			
			//populate string
			String html10 = populateHTML("../HTML/documents.html");
			
			response.getWriter().write(html10);
			
		case "upload": // sends to add
			
			//populate string
			String html11 = populateHTML("../HTML/add.html");
			
			response.getWriter().write(html11);
			
		case "upload complete": // sends to documents
			
			//populate string
			String html12 = populateHTML("../HTML/documents.html");
			
			response.getWriter().write(html12);
			
		case "delete": // sends to documents
			
			//populate string
			String html13 = populateHTML("../HTML/documents.html");
			
			response.getWriter().write(html13);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

	private String populateHTML(String path)
	{
		Scanner sc; // we will scan the html docs in each case and edit them before printing it using response writer
		String html = "";
		try
		{
			sc = new Scanner(new File(path));
			while(sc.hasNext())
				html += sc.next();
		}
		catch(Exception exe) {}
				
		return html;
	}
}
