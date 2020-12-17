
import java.io.IOError;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class User {

	private int id;
	private String password;
	
	public User(int id, String password) {
		this.id = id;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public void createBDD()
	{
		try
		{
			String CREATE_TABLE_SQL="CREATE TABLE messages ("
                    + "AUTHOR VARCHAR(45) NOT NULL,"
                    + "CONTACT VARCHAR(45) NOT NULL,"
                    + "DATEMESSAGE DATE NOT NULL,"
					+ "MESSAGE VARCHAR(45) NOT NULL)";
					
			String username = "tp_servlet_014";
			String password = "Died1zie";
			
			Connection con = null;
			/*Je ne suis pas sure du nom*/
			/*On load le driver*/
			Class.forName("com.mysql.jdbc.Driver");
			/*Mise en place de la connexion*/
			try
			{
				con=DriverManager.getConnection("jdbc:sqlite:MovieCatalog",username,password);
				Statement statement=con.createStatement();
				statement.executeUpdate(CREATE_TABLE_SQL);
				System.out.println("Table created");
				con.close();

			}
			catch(SQLException e)
			{
				System.err.println(e);
			}
			
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Erreur dans la creation de la BDD");
		}

	}

	
	
}


