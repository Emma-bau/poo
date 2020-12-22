import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.ResultSetMetaData;

import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DataManager {
	/*Need to be connected to INSA VPN*/
	private Agent agent;
	private ArrayList<Message> messagesHistory;
	
	Connection con = null;
	Statement statement;
	
	/*Information of connection*/
	private String username = "tp_servlet_014";
    private String password = "Died1zie";
    private String url = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/tp_servlet_014?useSSL=false";
   
    /*Table*/
    /*Revoir la taille des messages*/
    private final String message_table=
    "CREATE TABLE messages ("
            + "AUTHOR INTEGER NOT NULL,"
            + "CONTACT INTEGER NOT NULL,"
            + "DATEMESSAGE DATE NOT NULL,"
            + "MESSAGE VARCHAR(200) NOT NULL)";
    
	public DataManager(Agent agent) {
		this.agent = agent;
		this.messagesHistory = new ArrayList<Message>();
		/*Connexion to BDD, check contain of table*/
		
		connexion();
		delete_contain();
		/*loadHistory(101);*/
		/*createBDD();*/
		/*delete_table();*/
	}
	
	public ArrayList<Message> getMessagesHistory(){
		return messagesHistory;
	}

	public void connexion()
	{
		try
		{
			/*Load driver*/
			Class.forName("com.mysql.jdbc.Driver");
			try
			{
				con=DriverManager.getConnection(url,username,password);
				System.out.println("Connected database successfully");
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
	
	
	/*Used one time for table creation*/
	public void createBDD()
	{
		 ArrayList listCol=new ArrayList();
		 try
			{
			/*On creer notre table une fois dans notre bdd grâce à cette ligne*/
			statement.executeUpdate(message_table);
			
			/*On vérifie que notre table est bien créer et ce qu'il y a dedans*/
			System.out.println("Table created");
			// Créer un objet MetaData de ResultSet
		    ResultSet res=statement.executeQuery("Select * from  messages ");
		    ResultSetMetaData rsMetaData=(ResultSetMetaData) res.getMetaData();
		    // Accéder à la liste des colonnes
		    int nbrColonne = rsMetaData.getColumnCount();
		    for (int i = 1; i <= nbrColonne; i++)
		    {
		        // Retourner le nom de la colonne
		        String nom=rsMetaData.getColumnName(i);
		        // Retourner le type de la colonne
		        String type=rsMetaData.getColumnTypeName(i);
		        listCol.add(nom+" "+type);
		    }
	       // Afficher les noms et les types des colonne sur le console
	       System.out.println(listCol);
		}
		catch(SQLException e)
		{
			System.err.println(e);
			e.printStackTrace();

		}
	}
	
	public void add (Message m)
	{
		try 
		{
			String sql = "INSERT INTO messages (AUTHOR, CONTACT, DATEMESSAGE, MESSAGE) VALUES ("+String.valueOf(m.getAuthor().getId())+","+String.valueOf(m.getReceiver().getId())+",'"+m.getTimestamp()+"','"+m.getMessage()+"')";
			int statut = statement.executeUpdate(sql);
			
			System.out.println("Table entree ajoutee");
			/*Affichage de la table*/
			ResultSet RS = statement.executeQuery("SELECT AUTHOR, CONTACT, DATEMESSAGE, MESSAGE FROM messages");
			while(RS.next()) {
              System.out.println(RS.getInt("AUTHOR"));
              System.out.println(RS.getInt("CONTACT"));
              System.out.println(RS.getDate("DATEMESSAGE"));
              System.out.println(RS.getString("MESSAGE"));
			}
			RS.close(); 
			if (statut==0)
			{
				System.out.println("Aucune entree ajouter à la table");
			}
			
		}
		catch(SQLException e)
		{
			System.err.println(e);
			e.printStackTrace();
		}
		
	}
	
	public void updateMessagesHistory(Contact contact, String texte) {
		System.out.println("update du datamanager");
		Message m = new Message(texte, LocalDate.now(),contact,agent.getSelf());
		this.messagesHistory.add(m);
		agent.getInterfaceManager().getMainInterface().updateChatSessionMessages(m);
		
	}

	public ArrayList<Message> loadHistory(int id)
	{
		ArrayList<Message> message = new ArrayList<Message>();
		/*a voir l'ordre*/
		try {
			String sql = "SELECT * from messages WHERE AUTHOR = '"+String.valueOf(id) +"' OR CONTACT = '"+String.valueOf(id)+"' ";
			ResultSet rs =statement.executeQuery(sql);
			while (rs.next())
		     {
				
		        int author = rs.getInt("AUTHOR");
		        String messages = rs.getString("MESSAGE");
		        int contact = rs.getInt("CONTACT");
		        Date dateFirst = rs.getDate("DATEMESSAGE");
		        LocalDate date = dateFirst.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		        Contact destinataire=null;
		        Contact auteur=null;
		        /*recherche de l'auteur et du destinateur afin de creer les contacts*/
		        for(Contact C : agent.getNetworkManager().getconnectedUser())
		        {
		        	if(author==C.getId())
		        	{
		        		auteur=C;
		        	}
		        	if(contact==C.getId())
		        	{
		        		destinataire=C;
		        	}
		        }
		        if(auteur==null || destinataire==null)
		        {
		        	System.out.println("Erreur dans la recherche de contact associe a la BDD");
		        }
		        else 	
		        {
		        	 Message m = new Message(messages, date, auteur, destinataire);
		        	 message.add(m);
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
			/*Affichage de la table*/
			ResultSet RS = statement.executeQuery("SELECT AUTHOR, CONTACT, DATEMESSAGE, MESSAGE FROM messages");
			while(RS.next()) {
              System.out.println(RS.getInt("AUTHOR"));
              System.out.println(RS.getInt("CONTACT"));
              System.out.println(RS.getDate("DATEMESSAGE"));
              System.out.println(RS.getString("MESSAGE"));
			}
			RS.close(); 
		}
		catch(SQLException e)
		{
			System.err.println(e);
			e.printStackTrace();
		}
		
	}
}
