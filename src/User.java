
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
                    + "MESSAGE DATE NOT NULL,"
                    + "EMAIL VARCHAR(45) NOT NULL,"
					+ "PRIMARY KEY (UID))";
			
			Connection con = null;
			/*Je ne suis pas sure du nom*/
			/*On load le driver*/
			Class.forName("oracle.jdbc.driver.OracleDriver");
			/*Mise en place de la connexion*/
			try
			{
				con=DriverManager.getConnection("jdbc:sqlite:MovieCatalog");
				Statement statement=con.createStatement();

				
				String query= "SELECT *" + " FROM table";
				Statement stmt= con.createStatement();
				ResultSet rs= stmt.executeQuery(query);
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


