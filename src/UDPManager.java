import java.io.*;
import java.net.*;
import java.util.*;



public class UDPManager extends Thread{
	
	private int portNumReception; //= 65534;
	private int portNumEnvoie; //= 65535;
	private DatagramSocket udpSocket;
	private InetAddress adress;
	private NetworkManager manager;

	private static final int CHANGE_LOGIN = 0;
	private static final int CONNEXION = 1;
	private static final int DECONNEXION= 2;






	public UDPManager(int numPort, NetworkManager net ) throws SocketException
	{
		//Port de broadcast de tous les utilisateurs : 65535 pour envoyer
		//65534 pour recevoir
		this.portNumReception=numPort;
		this.manager=net;
		this.udpSocket = new DatagramSocket(portNumEnvoie);	
	}
	
	/*Revoir avec nouvelle norme*/
	public void broadcast(String message, InetAddress address, int portNum) throws IOException
	{
		udpSocket.setBroadcast(true);
		byte [] buffer = message.getBytes();
		DatagramPacket packet = new DatagramPacket (buffer, buffer.length, address, portNum);
		udpSocket.send(packet);
		udpSocket.close();
		
	}

	public void run()
	{
		try
		{
			adress = InetAddress.getByName("255.255.255.255");
		}
		catch(UnknownHostException e)
		{
			System.out.println("Erreur dans le broadcast, hote inconnu");
		}
		try
		{
			for (int i=65335; i>65235;i++)
			{
				broadcast("Hello",adress , portNumReception);
			}
		}
		catch(IOException e )
		{
			System.out.println("Erreur envoie du message en broadcast");
		}
		//Création de notre serveur UDP en écoute sur le port 65534
		try{
			DatagramSocket dgramSocket = new DatagramSocket(portNumReception);
			byte[] buffer = new byte[256];
			DatagramPacket inPacket = new DatagramPacket(buffer,buffer.length);
			try
			{

				dgramSocket.receive(inPacket);
				//Réception de l'adresse et du port associé//
				InetAddress clientAddress = inPacket.getAddress();
				//broadcast numéro port
				int clientPort = inPacket.getPort();
				String pseudo="";
				for(int i=1; i<buffer.length; i++)
				{
					pseudo += (char)buffer[i];
				}
				//Changement de login//
				if(buffer[0]==CHANGE_LOGIN)
				{
					update_contact(clientAddress,clientPort,pseudo);
					//Changer le pseudo à envoyer à l'interface//
				}
				//Nouvelle Connexion
				else if(buffer[0]==CONNEXION)
				{
					create_contact(clientAddress,clientPort,pseudo);

				}
				else if(buffer[0]==DECONNEXION)
				{
					remove_contact(clientAddress, clientPort, pseudo);
				}
				else       
				{
					System.out.println("Problème avec le broadcast, non lecture du buffer");
				}
				
			}	
			catch(IOException e)
			{
				System.out.println("Erreur au niveau du receive du Datagramme");
			}
		}
		catch(SocketException e)
		{
			System.out.println("Erreur création du datagramme");
		}
		
		
		
		
		




		
		
	}


	public void update_contact(InetAddress clientAddress, int clientPort, String pseudo)
	{
		ArrayList<Contact> connectedUser = manager.getconnectedUser();
		for (Contact c : connectedUser)
		{
			if(c.getAdresse() == clientAddress)
			{
				c.setPseudo(pseudo);
			}
		}
	}

	public void create_contact(InetAddress clientAddress, int clientPort, String pseudo)
	{
		ArrayList<Contact> connectedUser = manager.getconnectedUser();
		Contact C = new Contact(clientPort,pseudo,clientAddress);
		connectedUser.add(C);

			
	}

	public void remove_contact(InetAddress clientAddress, int clientPort, String pseudo)
	{
		ArrayList<Contact> connectedUser = manager.getconnectedUser();
		for(Contact c : connectedUser)
		{
			if(c.getAdresse() == clientAddress)
			{
				connectedUser.remove(c);
			}
		}	
	}
	
	
	

}
