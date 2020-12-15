import java.util.*;
import java.net.*;



public class NetworkManager extends Thread {

	private Agent agent;

	private ArrayList<Contact> connectedUser ;
	private ArrayList<Message> message_recu;

	private Message SendMessage;
	private Message ReceiveMessage;
	private UDPManager udpserver;
	
	//le server sera toujours sur ce numero de port
	// define the range 
    int max = 3000; 
    int min = 2000; 
	int range = max - min + 1; 
	
	private int numPort;

	private boolean Connexion = true;
	
	
	//getter and setter// 

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

	public int getNumPort() {
		return numPort;
	}

	public void setNumPort(int numPort) {
		this.numPort = numPort;
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
	
	
	/* Constructeur*/
	
	public NetworkManager (Agent agent) 
	{
		this.agent = agent;
		this.connectedUser = new ArrayList<Contact>();
		this.message_recu= new ArrayList<Message>();
		numPort = (int)(Math.random() * range) + min;
		
		try {
			InetAddress adress = InetAddress.getLocalHost();
			Contact c1 = new Contact(6,"courgette",adress);
			Contact c2 = new Contact(6,"salut",adress);
			Contact c3 = new Contact(6,"toto",adress);

			try
			{
			
				udpserver = new UDPManager(this);
				udpserver.start();
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
		/*ServerHandler server = new ServerHandler(this, numPort);
		server.start();	*/
	}
	
	public void connexion_tcp(Message message)
	{
		Contact user = message.getContact();
		ClientHandler client = new ClientHandler(this,user.getTcp_serv_port(),user.getAdresse());
		client.start();
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
