package main;
import javax.swing.*;

import controller.DataManager;
import controller.IDManager;
import controller.InterfaceManager;
import controller.NetworkManager;
import controller.PseudoManager;
import model.Contact;
import model.Message;

import java.net.*;

public class Agent {

	private InterfaceManager interfaceManager;
	private IDManager idManager;
	private DataManager dataManager; 
	private PseudoManager pseudoManager;
	private NetworkManager networkManager;
	private Contact self;
	//private User user; 

	public Agent() {

		this.idManager = new IDManager();
		this.dataManager = new DataManager(this);
		this.pseudoManager = new PseudoManager(this);	
		this.networkManager = new NetworkManager(this);
		
		try {
			InetAddress adress = InetAddress.getLocalHost();
			Contact self = new Contact(9999,9999,"blank",adress,101);
			this.self = self;
		} catch (UnknownHostException e) {}
		
		
		try {
			this.interfaceManager = new InterfaceManager(this);
			interfaceManager.getFrame().setSize(400,150);
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public InterfaceManager getInterfaceManager() {
		return interfaceManager;
	}
	public IDManager getIdManager() {
		return idManager;
	}
	public DataManager getDataManager() {
		return dataManager;
	}
	public PseudoManager getPseudoManager() {
		return pseudoManager;
	}
	public NetworkManager getNetworkManager() {
		return networkManager;
	}
	public Contact getSelf() {
		return self;
	}
	public void setSelf(Contact self) {
		this.self = self;
	}

	public int setPseudo(String pseudo) {
		int pseudoTest = pseudoManager.setPseudo(pseudo);
		if (pseudoTest == 0) {			
			networkManager.getUdpserver().first_connexion(pseudo);
		}
		return pseudoTest;
	}
	
	public void establishConnexion(Contact c) {
		networkManager.connexion_tcp(c);
	}
	
	public void sendMessageTo(Message m) {
		networkManager.sendMessage(m);
	}
	
	public void newMessageReceived(Contact contact, String text) {
		dataManager.updateMessagesHistory(contact, text);
	}
	
	public static void main(String[] args) {
		
		@SuppressWarnings("unused")
		Agent main = new Agent();
		

	}


}
