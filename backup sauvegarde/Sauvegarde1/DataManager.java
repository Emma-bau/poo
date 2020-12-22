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
	/*Need to be connected to INSA VPN*/
	private Agent agent;
	private ArrayList<Message> messagesHistory;
	
	/*Information of connection*/
	private String username = "tp_servlet_014";
    private String password = "Died1zie";
    private String url = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/tp_servlet_014?useSSL=false";
   
    /*Table*/
    /*Revoir la taille des messages*/
    private final String message_table=
    "CREATE TABLE messages ("
            + "AUTHOR VARCHAR(45) NOT NULL,"
            + "CONTACT VARCHAR(45) NOT NULL,"
            + "DATEMESSAGE DATE NOT NULL,"
            + "MESSAGE VARCHAR(200) NOT NULL)";
    
    
    /*to delete after test*/
	public DataManager() {

	}
	
	public DataManager(Agent agent) {
		this.agent = agent;
		this.messagesHistory = new ArrayList<Message>();
		createBDD();
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
			Class.forName("com.mysql.jdbc.Driver");
			/*Mise en place de la connexion*/
			try
			{
				con=DriverManager.getConnection(url,username,password);
				System.out.println("Connected database successfully");
				Statement statement=con.createStatement();
				/*On creer la table qui contient l'historique des messages*/
				statement.executeUpdate(message_table);
				System.out.println("Table created");
				/*On ferme la BDD*/
				statement.close();
				con.close();

			}
			catch(SQLException e)
			{
				System.err.println(e);
				e.printStackTrace();
+
			}
			
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Erreur dans la creation de la BDD");
		}
	}
	
	public void updateMessagesHistory(Contact contact, String texte) {
		System.out.println("update du datamanager");
		Message m = new Message(texte, LocalDate.now(),contact,agent.getSelf());
		this.messagesHistory.add(m);
		agent.getInterfaceManager().getMainInterface().updateChatSessionMessages(m);
		
	}
}
