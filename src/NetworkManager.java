import java.util.*;
import java.net.*;



public class NetworkManager extends Thread {
	private ArrayList<Contact> connectedUser = new ArrayList<Contact>();
	private ArrayList<Message> message_recu= new ArrayList<Message>();
	private Message SendMessage;
	private Message ReceiveMessage;
	private Agent agent;
	private int numPort = 2001;

	private boolean change_pseudo = false;
	private boolean Connexion = true;
	
	
	//getter and setter// 
	
	public Agent getAgent() {
		return agent;
	}

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
	
	public Message getReceiveMessage ()
	{
		return ReceiveMessage;
	}
	
	public void notifyPseudoChange(String pseudo)
	{
		change_pseudo = true;
	}
	
	public boolean isChange_pseudo() {
		return change_pseudo;
	}

	public void setPseudo_change(boolean change)
	{
		this.change_pseudo = change;
	}

	/* Constructeur*/
	
	public NetworkManager (int Numport, Agent agent) 
	{
		this.agent = agent;
		try {
			InetAddress adress = InetAddress.getLocalHost();
			Contact c1 = new Contact(6,"courgette",adress);
			Contact c2 = new Contact(6,"salut",adress);
			Contact c3 = new Contact(6,"toto",adress);
			
			connectedUser.add(c1);
			connectedUser.add(c2);
			connectedUser.add(c3);
		}
		catch(UnknownHostException e) {
			
		}
	
		//On envoie en broadcast notre connexion, et on creer notre serveur udp en ecoute//
		try{
			UDPManager udpserver = new UDPManager(Numport,this);
			udpserver.start();
		}
		catch(SocketException e)
		{
			System.out.println("Erreur des le debut");
		}

		//Creation de notre serveur tcp
		ServerHandler server = new ServerHandler(this);
		server.start();	
	}
	


	public void connexion(String pseudo)
	{
		for(Contact C : connectedUser)
		{
			if (pseudo == C.getPseudo())
			{	
				ClientHandler client = new ClientHandler(this,numPort,C.getAdresse());
				client.start();
			}
		}
		
	}

	public void message_reception()
	{
		if(ReceiveMessage != null)
		{
			message_recu.add(ReceiveMessage);
		}
	}

	public void run()
	{
		while (Connexion)
		{
			message_reception();
		}
	}


	
	

}
