
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
			Connection con = null;
			/*Je ne suis pas sure du nom*/
			/*On load le driver*/
			Class.forName("org.sqlite.JBDC");
			/*Mise en place de la connexion*/
			try
			{
				con=DriverManager.getConnection("jdbc:sqlite:MovieCatalog");
				Statement statement=con.createStatement();

				
				//ResultSet rs=statement.executeQuery("SELECT*"+FROMtable);
				/*On ferme la BDD*/
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


