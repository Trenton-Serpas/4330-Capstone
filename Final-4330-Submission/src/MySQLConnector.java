import java.sql.*;


public class MySQLConnector 
{
	String username = null;
	String password = null;
	Connection con;
	
	
	public MySQLConnector(String user, String pass, String database)
	{
		username = user;
		password = pass;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (ClassNotFoundException e1) {
			System.out.println("Error: Unable to find DB connector");
			System.out.println(e1);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, username, password);
		}
		catch(SQLException e)
		{
			System.out.println("Error: Unable to connect to DB");
			System.out.println(e);
		}
		
	}
	
	//con.close can throw an sql exception, but we don't really mind as closing isn't vital because of garbage collection
	//This motivated me to make close it's own method, it serves to contain those exceptions to one spot.
	public void close()
	{
		try { con.close(); }   //Sometimes terrible formatting is ok :p
		catch (SQLException e) { System.out.println("Error: Couldn't close connection."); }
	}
	
	
	//Executes the passed statement, and returns a string.  The format of the string is as follows
	//column 1 name|| column 2 name|| etc
	//row1 column 1 data|| row1 column 2 data|| etc
	//For as many rows and columns as you have.  
	public String retrieve(String executeThis)
	{
		String doc = "";
		try 
		{
			//Class.forName("com.mysql.jdbc.Driver");  This is depriciated apparently  
			//System.out.println("ok..."); 
			
			Statement stmt = con.createStatement();  
			ResultSet rs = stmt.executeQuery(executeThis);  
			if(!rs.next())
				return "";
			
			ResultSetMetaData metaData = rs.getMetaData();
			int count = metaData.getColumnCount(), i;
			
			//This section records the results of the statement into doc.  Currently it also prints to the console;
			if(count > 0)
			{
				//The first line is column names
				for(i = 1; i < count; i++)
					doc += metaData.getColumnName(i) + "||";
				doc += metaData.getColumnName(i)+"\n";
				
				//Loop through the rows until rs.next returns false
				do
				{
					for(i = 1; i < count; i++)
						doc += rs.getString(i) + "||";
					doc += rs.getString(count) + "\n";
				} while(rs.next());
				
			}
			 
		}
		catch(Exception e)
		{ 
			System.out.println("uh oh\n"+e); 
			return "Error";
		}  
		return doc;
	}
	
	public boolean add(String title, String content, String[] keywords)
	{
		//String sanitizedKeywords;  On second thought, parser.getTopTen always returns an array with 10 elems
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", username, password);
            Statement stmt = con.createStatement(); 
            stmt.executeUpdate("insert into test.documents (title, content, keyword1, keyword2, keyword3, keyword4, keyword5, keyword6, keyword7, keyword8, keyword9, keyword10) "  
            + "values('" + title + "', '" + content + "', '" + keywords[0] + "', '" + keywords[1] + "', '" + keywords[2] + "', '" + keywords[3] + "', '" + keywords[4] + "', '" 
            + keywords[5] + "', '" + keywords[6] + "', '" + keywords[7] + "', '" + keywords[8] + "', '" + keywords[9] + "')");
        }
        catch(Exception e){
            System.out.println("Failed to add to MySQL DB."); 
            return false;
        }
        
        return true;
    }
	
	public void addUser(String email, String pass)
	{
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", username, password);
            Statement stmt = con.createStatement(); 
            stmt.executeUpdate("insert into test.users (pass, email)  values('" + password + "', '" + email+ "')");
        }
        catch(Exception e){
            System.out.println("Failed to add to MySQL DB."); 
        }
	}
	
}
