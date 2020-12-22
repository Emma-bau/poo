package controller;
import java.util.*;

import main.Agent;
import model.Contact;
import model.Message;
import network.ClientTCPHandler;
import network.ServerTCPThread;
import network.ServerTCPHandler;
import network.UDPHandler;

import java.net.*;



public class NetworkManager extends Thread {

	private Agent agent;

	/*LIste de tous les contacts connectes*/
	private ArrayList<Contact> connectedUser ;
	/*C'est nous qui avons initier la connexion*/
	private ArrayList<ClientTCPHandler> connectedClient = new ArrayList<ClientTCPHandler>();
	/*C'est un usage qui l'a initier*/
	private ArrayList<ServerTCPThread> connectedNetwork = new ArrayList<ServerTCPThread>();
	/*Liste des messages recus*/
	private ArrayList<Message> message_recu;

	private Message ReceiveMessage;
	private UDPHandler udpserver;
	private int numPortTcp; 
	private boolean Connexion = true;
	private int numClient = 0;
	private int numWaiter = 0;
	
	/*Def du serveur pour le num port, pour utilisation sur un seul pc */
	// define the range of the server
    int max = 3000; 
    int min = 2000; 
	int range = max - min + 1; 
	
	
	
	
	//getter and setter// 

	public Agent getAgent() {
		return agent;
	}


	public ArrayList<ServerTCPThread> getConnectedNetwork() {
		return connectedNetwork;
	}

	public void setConnectedNetwork(ArrayList<ServerTCPThread> connectedNetwork) {
		this.connectedNetwork = connectedNetwork;
	}

	public ArrayList<Contact> getconnectedUser()
	{
		return this.connectedUser;
	}
	
	public void setconnectedUser(ArrayList<Contact> connectedUser )

	{
		this.connectedUser=connectedUser;
	}


	public UDPHandler getUdpserver() {
		return udpserver;
	}

	public void setUdpserver(UDPHandler udpserver) {
		this.udpserver = udpserver;
	}
	
	

	public void setReceiveMessage(Message recieveMessage) {

		ReceiveMessage = recieveMessage;
	}
	
	public boolean isConnexion() {
		return Connexion;
	}

	public void setConnexion(boolean connexion) {
		Connexion = connexion;
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

		try
		{
			//Creation de notre insatnce d'UDP
			udpserver = new UDPHandler(this);
		}
		catch(SocketException e)
		{
			System.out.println("Erreur des le debut avec le lancement de udp");
		}
			
		//Creation de notre serveur tcp
		ServerTCPHandler server = new ServerTCPHandler(this, numPortTcp);
		server.start();	
	}
	
	public void connexion_tcp(Contact contact)
	{
		connectedClient.add(numClient,new ClientTCPHandler(this,contact));
		connectedClient.get(numClient).start();
		contact.setNumClient(numClient);
		contact.setClient(true);
		numClient ++;		
	}

	public void sendMessage(Message message)
	{
		if(message.getReceiver().isClient())
		{
			connectedClient.get(message.getReceiver().getNumClient()).envoie(message);
		}
		/*else
		{
			connectedNetwork.get((message.getContact().getNumClient())//.envoie(message);
		}*/
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
