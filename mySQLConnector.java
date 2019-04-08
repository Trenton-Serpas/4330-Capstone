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
			

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", username, password);
			Statement stmt = con.createStatement();  
			ResultSet rs = stmt.executeQuery("show databases; ");  
			for(int i = 0; rs.next() && i <= index; i++) {}
				System.out.println((doc = rs.getString(2)));
			con.close();  
		}
		catch(Exception e)
		{ 
			System.out.println("uh oh"); 
			return "null";
		}  
		
		return doc;
	}
	
	//TODO: modify it to work for the new keyword system
	public void add(String title, String content, String[] keywords)
	{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", username, password);
            Statement stmt = con.createStatement(); 
            stmt.executeUpdate("insert into test.documents (title, content, keywords) values('"+title+"', '"+content+"', '"+keywords[0]+"')");
        }
        catch(Exception e){
            System.out.println(e); 
        }
    }
}
