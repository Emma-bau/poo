package controller;
import java.util.*;

import main.Agent;
import model.Contact;
import model.Message;
import network.ClientTCPHandler;
import network.ServerTCPThread;
import network.ServerTCPHandler;




public class NetworkManager extends Thread {

	/*-----------------------------------------------------Variable ----------------------------------------*/

	private Agent agent;

	/*List of all connected users*/
	private ArrayList<Contact> connectedUser ;
	/*List of all clientTCPHandler*/
	private ArrayList<ClientTCPHandler> connectedClient = new ArrayList<ClientTCPHandler>();
	/*List of all ServeurTCPHandler*/
	private ArrayList<ServerTCPThread> connectedNetwork = new ArrayList<ServerTCPThread>();
	
	/*We are online*/
	private boolean Connexion = true;
	/*0 connection at the beginning*/
	private int numClient = 0;
	private int numWaiter = 0;
	
	// define the range of the server tcp port for a use in one pc
    int max = 3000; 
    int min = 2000; 
	int range = max - min + 1; 
	private int numPortTcp; 

	/*----------------------------------------------------- Getter and setter ----------------------------------------*/

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
	
	public boolean isConnexion() {
		return Connexion;
	}

	public void setConnexion(boolean connexion) {
		Connexion = connexion;
	}
	
	public int getNumPortTcp() {
		return numPortTcp;
	}
	

	public int getNumWaiter() {
		return numWaiter;
	}

	public void setNumWaiter(int numWaiter) {
		this.numWaiter = numWaiter;
	}
	

	
	
	/*-----------------------------------------------------Constructor ----------------------------------------*/
	
	
	public NetworkManager (Agent agent) 
	{
		this.agent = agent;
		this.connectedUser = new ArrayList<Contact>();
		//Creation of our server tcp
		numPortTcp = (int)(Math.random() * range) + min;
		ServerTCPHandler server = new ServerTCPHandler(this, numPortTcp);
		server.start();	
	}
	

	/*-----------------------------------------------------Functions  ----------------------------------------*/
	
	/*Estbalished the connection with a client by a contact ( TCP) */
	public void connexion_tcp(Contact contact)
	{
		connectedClient.add(numClient,new ClientTCPHandler(this,contact));
		connectedClient.get(numClient).start();
		contact.setNumClient(numClient);
		contact.setClient(true);
		numClient ++;		
	}

	/*Use to send a message to another personn*/
	public void sendMessage(Message message)
	{
		if(message.getReceiver().isClient())
		{
			connectedClient.get(message.getReceiver().getNumClient()).send(message);
		}
	}

	



	
	

	
	

}
