import java.sql.*;


public class mySQLConnector 
{
	String username = null;
	String password = null;
	
	public mySQLConnector(String user, String pass)
	{
		username = user;
		password = pass;
	}
	
	public String retrieve(int index)
	{
		String doc;
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");  
			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/local", username, password);
			Statement stmt = con.createStatement();  
			ResultSet rs = stmt.executeQuery("show databases; ");  
			for(int i = 0; rs.next() && i <= index; i++) {}
				System.out.println((doc = rs.getString(2)));
			con.close();  
		}
		catch(Exception e)
		{ 
			System.out.println("oh fuck"); 
			return "null";
		}  
		
		return doc;
	}
	
	
}
