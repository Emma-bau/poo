import java.io.*;
import java.net.*;
import java.util.*;


public class NetworkManager extends Thread {
	private Agent agent;
	private ArrayList<Agent> connectedUser = new ArrayList<Agent>();
	private ArrayList<Message> messages = new ArrayList<Message>();
	private String SendMessage = "";
	private String ReceiveMessage;
	private boolean Connexion = true;
	private int numPort;
	
	
	
	//getter and setter//
	
	public void setNumPort(int numPort) {
		this.numPort = numPort;
	}

	/*Retourne la liste des utilisateurs connectés*/
	public ArrayList<Agent> getConnectedUser() {
		return connectedUser;
	}

	public void setConnectedUser(ArrayList<Agent> connectedUser) {
		this.connectedUser = connectedUser;
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}


	public boolean isConnexion() {
		return Connexion;
	}

	public void setConnexion(boolean connexion) {
		Connexion = connexion;
	}
	

	public String getSendMessage() {
		return SendMessage;
	}

	public void setSendMessage(String sendMessage) {
		SendMessage = sendMessage;
	}

	public String getReceiveMessage() {
		/*gérer avec data history*/
		return ReceiveMessage;
	}

	public void setReceiveMessage(String recieveMessage) {
		ReceiveMessage = recieveMessage;
	}
	
	public void notifyPseudoChange(String pseudo)
	{
		
	}
	
	
	/* Constructeur*/
	
	public NetworkManager(/*Agent agent*/) {
		/*this.agent = agent;*/
		//void connexion : on récupère toutes les personnes connectées//
		//On créer notre serveur//
		start();
		client();
	}

	
	
	
	/*Gestion du serveur*/
	

	public void run() 
	{
		try 
		{
			/*Création de notre serveur locale d'écoute*/
			ServerSocket serv = new ServerSocket(1234);
			/*On se met en écoute tant que la session est ouverte*/
			while(true)
			{
				/*Attente de connexion*/
				System.out.println("Awaiting connection");
				Socket SocketTCP = serv.accept();
				new Thread(new NetworkWaiter(SocketTCP,this)).start();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/*Gestion du client*/
	public void client()
	{
		try 
		{
			System.out.println("connexion au serveur coté client");
			/*adresse IP et num de port à récupérer, à faire le tableau des récupération et envoit au début*/
			Socket clientSocket = new Socket("127.0.0.1",1999);
			while(Connexion==true)
			{
				
				/*Si on a un message à envoyer*/
				if (getSendMessage()!="")
				{
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
					/*Fonction du message envoyé voulu*/
					out.println(getSendMessage());
					setSendMessage("");	
				}
				
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				/*ou si on reçoit un message, le buffer read_line n'est pas vide*/
				if(in.ready())
				{					
					String input = in.readLine();
					/*Network récupère notre message*/
					System.out.println("Received : "+input);
					setReceiveMessage(input);	
				}
				
			}
			
			
			
			
			
			
			
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	

}
