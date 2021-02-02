package network;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.NetworkManager;
import model.Contact;




public class UDPHandler extends Thread{
	// define the range 
    int max = 65500; 
    int min = 65300; 
	int range = max - min + 1; 
	
	// define the range 
    int max1 = 65300; 
    int min1 = 65000; 
	int range1 = max1 - min1 + 1; 
	
	
	private int portNumReception ;
	private int portNumEnvoie; 
	private InetAddress adress;
	private NetworkManager manager;

	private static final int CHANGE_LOGIN = 0;
	private static final int CONNEXION = 1;
	private static final int DECONNEXION= 2;
	private static final int ANSWER_CONNEXION = 3;



	public UDPHandler(NetworkManager net ) throws SocketException
	{
		//Port de broadcast de tous les utilisateurs : 65535 pour envoyer
		//65534 pour recevoir
		this.manager=net;
		portNumReception =  (int)(Math.random() * range) + min;
		portNumEnvoie = (int)(Math.random() * range1) + min1;
	}
	
	/*Revoir avec nouvelle norme*/
	public void broadcast(String message, InetAddress address, int portNum, DatagramSocket envoie) throws IOException
	{
		byte [] buffer = message.getBytes();
		DatagramPacket packet = new DatagramPacket (buffer, buffer.length, address, portNum);
		envoie.send(packet);	
	}

	public void run()
	{
		//Creation de notre serveur UDP en ecoute et envoie de notre premiere connexion	
		try
		{
			//Creation du port de reception
			DatagramSocket dgramSocketReception = new DatagramSocket(portNumReception);
			byte[] buffer = new byte[256];
			DatagramPacket inPacket = new DatagramPacket(buffer,buffer.length);
			try{
				System.out.println("Serveur UDP creer");
			
				while(manager.isConnexion())
				{
					dgramSocketReception.receive(inPacket);
					//Reception de l'adresse et du port associe//
					InetAddress clientAddress = inPacket.getAddress();
					
					//Recuperation des informations du message						
					String input="";
					for(int i=0; i<buffer.length; i++)
					{
						input += (char)buffer[i];
					}

					System.out.println(input);
					String etat_String = regexSearch("(?<=etat: )\\d+", input);
					String servPortUDP_String = regexSearch("(?<=servPort: )\\d+", input);
					String servPortTCP_String =  regexSearch("(?<=tcp: )\\d+", input);
					String id_String =  regexSearch("(?<=id: )\\d+", input);
					String pseudo = regexSearch("(?<=pseudo: )\\S+", input);
					
					int etat = Integer.parseInt(etat_String);
					int udpserv= Integer.parseInt(servPortUDP_String);
					int tcpserv = Integer.parseInt(servPortTCP_String);
					int id = Integer.parseInt(id_String);

					//Changement de login//
					if(etat==CHANGE_LOGIN)
					{
						update_contact(clientAddress,pseudo);
					}
					//Nouvelle Connexion
					else if(etat==CONNEXION || etat==ANSWER_CONNEXION )
					{
						create_contact(clientAddress,pseudo,udpserv,etat,tcpserv,id);

					}
					//Deconnexion d'un utilisateur//
					else if(etat==DECONNEXION)
					{
						remove_contact(clientAddress,pseudo);
					}
					else       
					{
						System.out.println("Probleme avec le broadcast, non lecture du buffer");
					}
					
				}
				dgramSocketReception.close();


				
			}
			catch(IOException e )
			{
				e.printStackTrace();
				System.out.println("Thread UDP socket");
			}
		}
		catch(SocketException e)
		{
			e.printStackTrace();
		}

	}

	public void update_contact(InetAddress clientAddress, String pseudo)
	{
		ArrayList<Contact> connectedUser = manager.getconnectedUser();
		for (Contact c : connectedUser)
		{
			if(c.getAdresse() == clientAddress)
			{
				c.setPseudo(pseudo);
			}
			c.afficher();
		}
		manager.setconnectedUser(connectedUser);	
	}

	public void create_contact(InetAddress clientAddress, String pseudo, int ServPort, int etat, int tcp, int id)
	{

		try{
			//Mise a jour de nos contacts//
			ArrayList<Contact> connectedUser = manager.getconnectedUser();

			Contact C = new Contact(ServPort,tcp,pseudo,clientAddress,id);
			connectedUser.add(C);
			manager.setconnectedUser(connectedUser);

			//Verification console//
			for (Contact c :  manager.getconnectedUser())
			{
				c.afficher();
			}

			//Si c'est une premiere connexion alors on repond, sinon c'est une reponse a notre premier envoie	
			if (etat == 1)
			{
				String message="etat: 3 servPort: "+portNumReception+"tcp: "+manager.getNumPortTcp()+"id: "+manager.getAgent().getSelf().getId()+"pseudo: "+manager.getAgent().getPseudoManager().getPseudo()+" final";
				byte [] buffer = message.getBytes();
				try
				{
					DatagramSocket envoie = new DatagramSocket(portNumEnvoie);
					DatagramPacket packet = new DatagramPacket (buffer, buffer.length, clientAddress, ServPort);
					envoie.send(packet);
					envoie.close();
				}
				catch(SocketException e)
				{
					System.out.println("Erreur message dans la reponse a une connexion");
				}
			}

		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.out.println("Erreur message dans la reponse a une connexion");
		}

		
	}

	public void remove_contact(InetAddress clientAddress, String pseudo)
	{
		ArrayList<Contact> connectedUser = manager.getconnectedUser();
		for(Iterator<Contact> it = connectedUser.iterator();it.hasNext();)
		{
			Contact c = (Contact)it.next();
			c.afficher();
			if(c.getAdresse() == clientAddress)
			{
				it.remove();
			}
		}
		manager.setconnectedUser(connectedUser);	
	}

	public static String regexSearch(String regex, String input) {
        Matcher m = Pattern.compile(regex).matcher(input);
        if (m.find()) return m.group();
        return null;
	}
	
	public void change_pseudo(String pseudo)
	{
		//On envoie en broadcast le changement de pseudo a tous les utilisateurs 
		String message = "etat: 0 servPort: "+portNumReception+" tcp: "+manager.getNumPortTcp()+"id: "+manager.getAgent().getSelf().getId()+"pseudo: "+pseudo+" final";

		try {
			DatagramSocket envoie = new DatagramSocket(portNumEnvoie);
			for (int i=65534; i>65233;i--)
			{
				if(i != portNumReception)
				{	
					broadcast(message,adress,i,envoie);
				}
			}
			envoie.close();
			manager.getAgent().getServerHandler().notifyServer(2);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("Probleme a l'envoi du nouveau pseudo");
		}
	}

	public void first_connexion (String pseudo)
	{
		//Envoie de la premiere connexion//
		try
		{
			start();
			/*Création du contact nous-même*/
			this.adress =  InetAddress.getByName("255.255.255.255");
			manager.getAgent().getSelf().setPseudo(pseudo);
			manager.getAgent().getSelf().setTcp_serv_port(manager.getNumPortTcp());
			manager.getAgent().getSelf().setUdp_serv_port(portNumReception);
			manager.getAgent().getSelf().setAdresse(InetAddress.getLocalHost());
			
		}
		catch(UnknownHostException e)
		{
			System.out.println("Erreur dans le broadcast, hote inconnu");
		}
		try{
			try 
			{
				DatagramSocket envoie = new DatagramSocket(portNumEnvoie);
				String message = "etat: 1 servPort: "+portNumReception+" tcp: "+manager.getNumPortTcp()+"id: "+manager.getAgent().getSelf().getId()+"pseudo: "+pseudo+" final";
				for (int i=65534; i>65233;i--)
				{
					if(i != portNumReception)
					{
					
						broadcast(message,adress,i,envoie);

					}
				}
				envoie.close();
				/*Notification au serveur de la connexion d'un nouvel utilisateur*/
				manager.getAgent().getServerHandler().notifyServer(1);
				
			}
			catch(SocketException e)
			{
				System.out.println("Probleme socket udp premier envoie");
			}
			
			
		}
		catch (IOException e)
		{
			System.out.println("PremiÃ¨re connexion erreur udp io");
		}
	}

	public void deconnexion (String pseudo)
	{
		//On envoie en broadcast le changement de pseudo a tous les utilisateurs 
		String message =  "etat: 2 servPort: "+portNumReception+" tcp: "+manager.getNumPortTcp()+"id: "+manager.getAgent().getSelf().getId()+"pseudo: "+pseudo+" final";
		try {
			DatagramSocket envoie = new DatagramSocket(portNumEnvoie);
			for (int i=65534; i>65233;i--)
			{
				if(i != portNumReception)
				{
				
					broadcast(message,adress,i,envoie);

				}
			}
			envoie.close();
			//Rajouter la deconnexion au servuer ici et tester les valeurs et le retrait de la liste//
			manager.getAgent().getServerHandler().notifyServer(0);
			
		}
		catch (IOException e)
		{
			System.out.println("Probleme a l'envoi du nouveau login");
		}
			
	}


}


