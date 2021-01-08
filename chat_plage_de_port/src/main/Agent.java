package main;
import javax.swing.*;

import controller.DataManager;
import controller.IDManager;
import controller.InterfaceManager;
import controller.NetworkManager;
import controller.PseudoManager;
import model.Contact;
import model.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.*;

public class Agent {

	private InterfaceManager interfaceManager;
	private IDManager idManager;
	private DataManager dataManager; 
	private PseudoManager pseudoManager;
	private NetworkManager networkManager;
	private Contact self;
	private boolean first_time_pseudo;
	//private User user; 

	public Agent() {

		/*this.idManager = new IDManager();
		this.dataManager = new DataManager(this);
		this.pseudoManager = new PseudoManager(this);	
		this.networkManager = new NetworkManager(this);
		this.first_time_pseudo = true;*/
		
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
		System.out.println("On arrive au serveur");
		notifyServer();
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
			if (this.first_time_pseudo == true) {
				networkManager.getUdpserver().first_connexion(pseudo);
				this.first_time_pseudo = false;
			}
			else {
				System.out.println("udpserver.change_pseudo: " + pseudo);
				interfaceManager.getMainInterface().updatePseudo();
				networkManager.getUdpserver().change_pseudo(pseudo);
			}
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
	
	public void deconnexion() {
		networkManager.getUdpserver().deconnexion(pseudoManager.getPseudo());
	}
	
	public static void notifyServer()
	{
		String url = "https://srv-gei-tomcat.insa-toulouse.fr/Server_Jacques_Baudoint/servlet";
		try
		{
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("cmd", "connected");
			connection.setRequestProperty("ID", "405");
			connection.setRequestProperty("pseudo","courgette");
			System.out.println(connection.getResponseCode());
			System.out.println("En vrai, arriver ici");
			connection.disconnect();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*A modifier mais fonctionne pour l'instant très bien, à voir comment rajouter les nouveaux utilisateurs*/
	public static void loadServer()
	{
		String url = "https://srv-gei-tomcat.insa-toulouse.fr/Server_Jacques_Baudoint/servlet";
		String msg ;
		try
		{
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("GET");
			if(connection.getResponseCode()==200)
			{
				BufferedReader in= new BufferedReader (new InputStreamReader (connection.getInputStream()));
				while((msg=in.readLine())!=null)
				{
					System.out.println(msg);
				}
			}
			connection.disconnect();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		//Agent main = new Agent();
		notifyServer();
		try {
			Thread.sleep(5);
			loadServer();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
