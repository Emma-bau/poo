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

	/*Retourne la liste des utilisateurs connectes*/
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
		/*gerer avec data history*/
		return ReceiveMessage;
	}

	public void setReceiveMessage(String recieveMessage) {
		ReceiveMessage = recieveMessage;
	}
	
	public void notifyPseudoChange(String pseudo)
	{
		
	}
	
	
	/* Constructeur*/
	
	public NetworkManager(int Numport) 
	{

		//On creer notre serveur//
		ServerHandler server = new ServerHandler(Numport,this);
		server.start();
		//On creer notre client
		ClientHandler client = new ClientHandler(this);
		client.start();

	}

	
	
	
	/*Gestion du serveur*/
	

	
	
	
	
	
	

}
