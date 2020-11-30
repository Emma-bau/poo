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

	/*Retourne la liste des utilisateurs connect�s*/
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
		/*g�rer avec data history*/
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
		//void connexion : on r�cup�re toutes les personnes connect�es//
		//On cr�er notre serveur//
		start();
		client();
	}

	
	
	
	/*Gestion du serveur*/
	

	public void run() 
	{
		try 
		{
			/*Cr�ation de notre serveur locale d'�coute*/
			ServerSocket serv = new ServerSocket(1234);
			/*On se met en �coute tant que la session est ouverte*/
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
			System.out.println("connexion au serveur cot� client");
			/*adresse IP et num de port � r�cup�rer, � faire le tableau des r�cup�ration et envoit au d�but*/
			Socket clientSocket = new Socket("127.0.0.1",1999);
			while(Connexion==true)
			{
				
				/*Si on a un message � envoyer*/
				if (getSendMessage()!="")
				{
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
					/*Fonction du message envoy� voulu*/
					out.println(getSendMessage());
					setSendMessage("");	
				}
				
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				/*ou si on re�oit un message, le buffer read_line n'est pas vide*/
				if(in.ready())
				{					
					String input = in.readLine();
					/*Network r�cup�re notre message*/
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
