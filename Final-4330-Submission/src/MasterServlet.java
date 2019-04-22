import javax.mail.*;
import javax.mail.internet.*;
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


	MySQLConnector mc;
	public void init() throws ServletException 
	{
	      mc = new MySQLConnector("Andrew", "cbasfish", "test");
	      
	}
	
	
    public MasterServlet() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String type = request.getParameter("type"); // this can also be done using different servlets
		Scanner sc; // we will scan the html docs in each case and edit them before printing it using response writer
		

		
		if(type == null)
			return;
		
		switch(type)
		{
		case "create"://sends to Documents
			
			sc = new Scanner(new File("../HTML/documents.html"));
			
			//create user
			boolean creationSuccess = createUser(request.getParameter("email"), request.getParameter("pass"));
			
			//fetch titles
			List<String> titlesC = getTitles();
			
			//update default titles
			
			
			response.getWriter().write("");
			
		case "login"://sends to Documents
			
			//verify user
			boolean loginSuccess = login(request.getParameter("email"), request.getParameter("pass"));
			
			//fetch titles
			List<String> titlesL = getTitles();
			
			//update default titles
			
			
		case "email":
			Properties properties = System.getProperties();
			properties.setProperty("mail.smtp.host", "localhost");
			Session session = Session.getDefaultInstance(properties);
			
			String to = "abc@gmail.com";
			String pass = "password123";
			
			try
			{
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress("wordcloud@gmail.com"));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				message.setSubject("word cloud account password");
				message.setText(pass);
				Transport.send(message);
			}
			
			catch(MessagingException mex)
			{
				
			}
			
		case "titles":
			
		case "titleAndFullbody":
			
		case "wordcloud":
			
		case "search":
			
		case "upload":
			
		case "delete":
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
