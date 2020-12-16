import java.util.*;
import java.net.*;



public class NetworkManager extends Thread {

	private Agent agent;

	/*LIste de tous les contacts connectes*/
	private ArrayList<Contact> connectedUser ;
	/*C'est nous qui avons initier la connexion*/
	private ArrayList<ClientHandler> connectedClient = new ArrayList<ClientHandler>();
	/*C'est un usage qui l'a initier*/
	private ArrayList<NetworkWaiter> connectedNetwork = new ArrayList<NetworkWaiter>();
	/*Liste des messages recus*/
	private ArrayList<Message> message_recu;

	private Message ReceiveMessage;
	private UDPManager udpserver;
	private int numPortTcp;  	//Serveur tcp
	private boolean Connexion = true;
	private int numClient = 0;
	private int numWaiter = 0;
	
	//le server sera toujours sur ce numero de port
	// define the range of the server
    int max = 3000; 
    int min = 2000; 
	int range = max - min + 1; 
	
	
	
	
	//getter and setter// 

	public ArrayList<NetworkWaiter> getConnectedNetwork() {
		return connectedNetwork;
	}

	public void setConnectedNetwork(ArrayList<NetworkWaiter> connectedNetwork) {
		this.connectedNetwork = connectedNetwork;
	}


	public UDPManager getUdpserver() {
		return udpserver;
	}

	public void setUdpserver(UDPManager udpserver) {
		this.udpserver = udpserver;
	}

	
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

	public boolean isConnexion() {
		return Connexion;
	}

	public void setConnexion(boolean connexion) {
		Connexion = connexion;
	}
	
	public void setReceiveMessage(Message recieveMessage) {
		ReceiveMessage = recieveMessage;
	}
	
	public Message getReceiveMessage ()
	{
		return ReceiveMessage;

	}

	public int getNumPortTcp() {
		return numPortTcp;
	}
	
	
	/* Constructeur*/
	
	public NetworkManager (Agent agent) 
	{
		this.agent = agent;
		this.connectedUser = new ArrayList<Contact>();
		this.message_recu= new ArrayList<Message>();
		numPortTcp = (int)(Math.random() * range) + min;
		System.out.println(numPortTcp);
		
		try {
			InetAddress adress = InetAddress.getLocalHost();
			Contact c1 = new Contact(6,7,"courgette",adress);
			Contact c2 = new Contact(6,7,"salut",adress);
			Contact c3 = new Contact(6,7,"toto",adress);

			try
			{
				udpserver = new UDPManager(this);
			}
			catch(SocketException e)
			{
				System.out.println("Erreur des le debut avec le lanceùent de udp");
			}
			
			connectedUser.add(c1);
			connectedUser.add(c2);
			connectedUser.add(c3);
		}
		catch(UnknownHostException e) {
			
		}
	

		//Creation de notre serveur tcp
		ServerHandler server = new ServerHandler(this, numPortTcp);
		server.start();	
	}
	
	public void connexion_tcp(Contact contact)
	{
		connectedClient.add(numClient,new ClientHandler(this,contact));
		connectedClient.get(numClient).start();
		contact.setNumClient(numClient);
		contact.setClient(true);
		for (ClientHandler c : connectedClient)
		{
			c.afficher();
		}
		numClient ++;		
	}

	public void sendMessage(Message message)
	{
		if(message.getContact().isClient())
		{
			connectedClient.get(message.getContact().getNumClient()).envoie(message);
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

	public int getNumWaiter() {
		return numWaiter;
	}

	public void setNumWaiter(int numWaiter) {
		this.numWaiter = numWaiter;
	}

	



	
	

	
	

}
