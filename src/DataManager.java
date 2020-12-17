import java.util.ArrayList;
import java.time.LocalDate;
import java.io.IOError;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DataManager {

	private Agent agent;
	private ArrayList<Message> messagesHistory;
	
	public DataManager(Agent agent) {
		this.agent = agent;
		this.messagesHistory = new ArrayList<Message>();
	}
	
	public ArrayList<Message> getMessagesHistory(){
		return messagesHistory;
	}


	public void createBDD()
	{
		try
		{
			Connection con = null;
			/*Je ne suis pas sure du nom*/
			/*On load le driver*/
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			/*Mise en place de la connexion*/
			try
			{
				con=DriverManager.getConnection("jdbc:odbc:MovieCatalog");
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
	
	public void updateMessagesHistory(Contact contact, String texte) {
		System.out.println("update du datamanager");
		Message m = new Message(texte, LocalDate.now(),agent.getSelf(),contact);
		System.out.println("update du datamanager 2");
		this.messagesHistory.add(m);
		System.out.println("update du datamanager 3");
		agent.getInterfaceManager().getMainInterface().updateChatSessionMessages(m);
		
	}
	
	
}
