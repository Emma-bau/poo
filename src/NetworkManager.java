import java.util.*;


public class NetworkManager extends Thread {
	private Agent agent;
	private ArrayList<Contact> connectedUser = new ArrayList<Contact>();
	private Message SendMessage;
	private Message ReceiveMessage;
	private boolean Connexion = true;
	private int numPort = 2001;
	
	
	
	//getter and setter//
	public void setconnectedUser(ArrayList<Contact> connectedUser )
	{
		this.connectedUser=connectedUser;
	}

	public ArrayList<Contact> getconnectedUser()
	{
		return this.connectedUser;
	}

	public void setNumPort(int numPort) {
		this.numPort = numPort;
	}

	public boolean isConnexion() {
		return Connexion;
	}

	public void setConnexion(boolean connexion) {
		Connexion = connexion;
	}
	
	public Message getSendMessage() {
		return SendMessage;
	}

	public void setSendMessage(Message sendMessage) {
		SendMessage = sendMessage;
	}

	public void setReceiveMessage(Message recieveMessage) {
		ReceiveMessage = recieveMessage;
	}
	
	public void notifyPseudoChange(String pseudo)
	{
		
	}
	
	
	/* Constructeur*/
	
	public NetworkManager(int Numport) 
	{
		//On creer notre serveur//
		/*UDPManager udpserver = new UDPManager(Numport);
		udpserver.start();*/
		ServerHandler server = new ServerHandler(this);
		server.start();
		//On creer notre client à la demande d'une connexion
		
	}

	public void connexion()
	{
		if(Connexion)
		{
			ClientHandler client = new ClientHandler(this,numPort);
			client.start();
		}
	}


}
