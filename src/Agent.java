import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class Agent {

	private InterfaceManager interfaceManager;
	private IDManager idManager;
	private DataManager dataManager; 
	private PseudoManager pseudoManager;
	private NetworkManager networkManager;
	private Contact self;

	public Agent() {

		this.idManager = new IDManager();
		this.dataManager = new DataManager(this);
		this.pseudoManager = new PseudoManager(this);	
		this.networkManager = new NetworkManager(this);
		
		try {
			InetAddress adress = InetAddress.getLocalHost();
			Contact self = new Contact(3000,4000,null,adress);
			this.self = self;
		} catch (UnknownHostException e) {}
		
		
		try {
			this.interfaceManager = new InterfaceManager(this);
			interfaceManager.setSize(400,150);
			interfaceManager.setVisible(true);
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

	public boolean setPseudo(String pseudo) {
		if (pseudoManager.setPseudo(pseudo)) {			
			networkManager.getUdpserver().first_connexion(pseudo);
			return true;
		}
		return false;
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

		//NetworkManager networkManager = new NetworkManager(numportServer);
		Agent main = new Agent();

	}

	public Contact getSelf() {
		return self;
	}

	public void setSelf(Contact self) {
		this.self = self;
	}
}
