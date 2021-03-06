package main;
import javax.swing.*;

import controller.DataManager;
import controller.IDManager;
import controller.InterfaceManager;
import controller.NetworkManager;
import controller.PseudoManager;
import servlet.ServerHandler;
import model.Contact;
import model.Message;

import java.net.*;

public class Agent {

	/*----------------------------------------------------- Variable ----------------------------------------*/
	
	private InterfaceManager interfaceManager;
	private IDManager idManager;
	private DataManager dataManager; 
	private PseudoManager pseudoManager;
	private NetworkManager networkManager;
	private ServerHandler serverHandler;
	private Contact self;
	private boolean first_time_pseudo, interne;


	/*----------------------------------------------------- Constructor ----------------------------------------*/
	public Agent() {

		this.idManager = new IDManager();
		this.dataManager = new DataManager(this);
		this.pseudoManager = new PseudoManager(this);	
		this.networkManager = new NetworkManager(this);
		this.serverHandler = new ServerHandler(this);
		this.first_time_pseudo = true;
		this.interne = false;

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
	

	/*----------------------------------------------------- Getter and setter ----------------------------------------*/

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
	public ServerHandler getServerHandler() {
		return serverHandler;
	}
	public Contact getSelf() {
		return self;
	}
	public void setSelf(Contact self) {
		this.self = self;
	}
	public boolean isInterne() {
		return interne;
	}

	public int setPseudo(String pseudo) {
		int pseudoTest = pseudoManager.setPseudo(pseudo);
		if (pseudoTest == 0) {		
			if (this.first_time_pseudo == true) {
				try {
					serverHandler.first_connexion(pseudo);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				this.first_time_pseudo = false;
			}
			else {
				System.out.println("udpserver.change_pseudo: " + pseudo);
				interfaceManager.getMainInterface().updatePseudo();
				serverHandler.change_pseudo(pseudo);
			}
		}
		return pseudoTest;
	}
	

	/*----------------------------------------------------- Function ----------------------------------------*/

	public void establishConnexion(Contact c) {
		networkManager.connexion_tcp(c);
	}

	public void sendMessageTo(Message m) {
		networkManager.sendMessage(m);
	}

	public void newMessageReceived(Contact contact, String text) {
		dataManager.updateMessagesHistory(contact, text);
	}

	public void deconnexion() {
		serverHandler.deconnexion(pseudoManager.getPseudo());
	}

	/*----------------------------------------------------- MAIN ----------------------------------------*/

	public static void main(String[] args) {

		@SuppressWarnings("unused")
		Agent main = new Agent();
	}


}
