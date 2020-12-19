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

	public DataManager() {

	}
	
	public DataManager(Agent agent) {
		this.agent = agent;
		this.messagesHistory = new ArrayList<Message>();
	}
	
	public ArrayList<Message> getMessagesHistory(){
		return messagesHistory;
	}

	private String username = "tp_servlet_014";
    private String password = "Died1zie";
    private String url = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/tp_servlet_014";
    //private String url = "jcbd:mysql://tp_servlet_014:Died1zie@srv-bdens.insa-toulouse.fr:3306/tp_servlet_0014.db";


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
		Message m = new Message(texte, LocalDate.now(),contact,agent.getSelf());
		this.messagesHistory.add(m);
		agent.getInterfaceManager().getMainInterface().updateChatSessionMessages(m);
		
	}
}
