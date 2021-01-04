package controller;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.PreparedStatement;

import main.Agent;
import model.Message;
import model.Contact;

import java.time.LocalDate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*Need to be connected to INSA VPN*/
public class DataManager {

	private Agent agent;
	private ArrayList<Message> messagesHistory;
	
	Connection con = null;
	Statement statement;
    PreparedStatement prSt = null;
	
	/*Information of connection*/
	private String username = "tp_servlet_014";
    private String password = "Died1zie";
    private String url = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/tp_servlet_014?useSSL=false";
   
    /*Table*/
    /*Revoir la taille des messages, pour l'instant à 48 je crois*/
    private final String message_table=
    "CREATE TABLE messages ("
            + "AUTHOR INTEGER NOT NULL,"
            + "CONTACT INTEGER NOT NULL,"
            + "DATEMESSAGE DATE NOT NULL,"
            + "MESSAGE VARCHAR(200) NOT NULL)";
    
	public DataManager(Agent agent) {
		this.agent = agent;
		this.messagesHistory = new ArrayList<Message>();
		/*Connexion to BDD*/
		connexion();
	}
	
	public ArrayList<Message> getMessagesHistory(){
		return messagesHistory;
	}

	public void connexion()
	{	try
		{
			/*Load driver*/
			Class.forName("com.mysql.jdbc.Driver");
			try
			{
				con=DriverManager.getConnection(url,username,password);
				statement=con.createStatement();	
			}
			catch(SQLException e)
			{
				System.err.println(e);
				e.printStackTrace();
			}	
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Erreur dans la creation de la BDD");
		}
	}
	
	/*use one time*/
	public void createBDD()
	{
		try
		{
			statement.executeUpdate(message_table);	
		}
		catch(SQLException e)
		{
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	/*voir comment rajouter une barre oblique pour chaque apostrophe*/
	public void add (Message m)
	{
		try 
		{	
		String query = "INSERT INTO messages (AUTHOR, CONTACT, DATEMESSAGE, MESSAGE) VALUES ("+String.valueOf(m.getAuthor().getId())+","+String.valueOf(m.getReceiver().getId())+",'"+m.getTimestamp()+"',?)";
		prSt = (PreparedStatement) con.prepareStatement(query);
		prSt.setString(1,m.getMessage());
		
			int statut =prSt.executeUpdate();
			if(statut == 0)
			{
				System.out.println("Aucune opération réalisé avec la BDD");
			}
		}
		catch(SQLException e)
		{
			System.err.println(e);
			e.printStackTrace();
		}
		
	}
	
	public void updateMessagesHistory(Contact contact, String texte) {
		Message m = new Message(texte, LocalDate.now(),contact,agent.getSelf());
		this.messagesHistory.add(m);
		agent.getInterfaceManager().getMainInterface().updateChatSessionMessages(m);
		
	}

	/*used to load message from author to recipient from database*/
	public ArrayList<Message> loadHistory(int auteur, int dest)
	{
		ArrayList<Message> message = new ArrayList<Message>();
		try {
			String sql = "SELECT * from messages WHERE (AUTHOR = '"+String.valueOf(auteur) +"' AND CONTACT = '"+String.valueOf(dest)+"') OR (AUTHOR = '"+String.valueOf(dest) +"' AND CONTACT = '"+String.valueOf(auteur)+"')";
			ResultSet rs =statement.executeQuery(sql);

			while (rs.next())
		     {
		        int author = rs.getInt("AUTHOR");
		        String messages = rs.getString("MESSAGE");
		        int contact = rs.getInt("CONTACT");
		        Date dateFirst = rs.getDate("DATEMESSAGE");
		        LocalDate date = new java.sql.Date(dateFirst.getTime()).toLocalDate();
		        
		        /*Look for the contact in our list*/
		        Contact destinataire=null;
		        Contact auteure=null;
		        for(Contact C : agent.getNetworkManager().getconnectedUser())
		        {
		        	if(author==C.getId())
		        	{
		        		auteure=C;
		        	}
		        	if(contact==C.getId())
		        	{
		        		destinataire=C;
		        	}
		        }
		        /*the one which is null is us, we can add the message to the list*/
		        if(auteure==null)
		        {
		        	Message m = new Message(messages, date, agent.getSelf(), destinataire);
		        	message.add(m);
		        }
		        else if (destinataire==null)
		        {
		        	Message m = new Message(messages, date, auteure, agent.getSelf());
		        	message.add(m);
		        }
		        else
		        {
		        	System.out.println("Erreur au niveau de la recherche d'auteur et de destinataire");
		        }
		     }
		}
		catch(SQLException e) 
		{
			System.err.println(e);
			e.printStackTrace();
		}
		return message;
	}
	
	
	/*To clean */
	public void delete_table()
	{
		try 
		{
			String sql = "DROP TABLE messages ";
			statement.executeUpdate(sql);
			System.out.println("Table deleted successfully");
		}
		catch(SQLException e)
		{
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	public void delete_contain()
	{
		try 
		{
			String sql = "DELETE from messages ";
			statement.executeUpdate(sql);
			System.out.println("Table erased");
		}
		catch(SQLException e)
		{
			System.err.println(e);
			e.printStackTrace();
		}
	}

}
