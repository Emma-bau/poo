import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class UDPManager extends Thread{
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



	public UDPManager(NetworkManager net ) throws SocketException
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
					String servPort_String = regexSearch("(?<=servPort: )\\d+", input);
					String servPortTCP =  regexSearch("(?<=tcp: )\\d+", input);
					String pseudo = regexSearch("(?<=pseudo: )\\S+", input);
					
					int etat = Integer.parseInt(etat_String);
					int servPort= Integer.parseInt(servPort_String);
					int tcpserv = Integer.parseInt(servPortTCP);

					//Changement de login//
					if(etat==CHANGE_LOGIN)
					{
						update_contact(clientAddress,pseudo);
						//Changer le pseudo a envoyer a l'interface//
					}
					//Nouvelle Connexion
					else if(etat==CONNEXION || etat==ANSWER_CONNEXION )
					{
						create_contact(clientAddress,pseudo,servPort,etat,tcpserv);

					}
					else if(etat==DECONNEXION)
					{
						remove_contact(clientAddress,pseudo);
					}
					else       
					{
						System.out.println("Problème avec le broadcast, non lecture du buffer");
					}
					
				}
				dgramSocketReception.close();


				
			}
			catch(IOException e )
			{
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
		}
	}

	public void create_contact(InetAddress clientAddress, String pseudo, int ServPort, int etat, int tcp)
	{

		try{
			//Mise a jour de nos contacts//
			ArrayList<Contact> connectedUser = manager.getconnectedUser();

			Contact C = new Contact(ServPort,tcp,pseudo,clientAddress);
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
				String message="etat: 3 servPort: "+portNumReception+"tcp: "+manager.getNumPortTcp()+"pseudo: "+manager.getAgent().getPseudoManager().getPseudo()+" final";
				byte [] buffer = message.getBytes();
				try
				{
					//Envoie de nos informations
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
			System.out.println("Erreur message dans la reponse a une connexion");
		}

		
	}

	public void remove_contact(InetAddress clientAddress, String pseudo)
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

	public static String regexSearch(String regex, String input) {
        Matcher m = Pattern.compile(regex).matcher(input);
        if (m.find()) return m.group();
        return null;
	}
	
	public void change_pseudo(String pseudo)
	{
		//On envoie en broadcast le changement de pseudo a tous les utilisateurs 
		String message = "etat: 0 servPort: "+portNumReception+"pseudo: "+pseudo;
		try {
			DatagramSocket envoie = new DatagramSocket(portNumEnvoie);
			for (int i=65335; i>65233;i--)
			{
				if(i != portNumReception)
				{
			
					broadcast(message,adress,i,envoie);
					

				}
			}
			envoie.close();
		}
		catch (IOException e)
		{
			System.out.println("Probleme a lenvoi du nouveau login");
		}
	}

	public void first_connexion (String pseudo)
	{
		//Envoie de la premiere connexion//
		try
		{
			start();
			adress = InetAddress.getByName("localhost");
		}
		catch(UnknownHostException e)
		{
			System.out.println("Erreur dans le broadcast, hote inconnu");
		}
		try{
			try 
			{
				DatagramSocket envoie = new DatagramSocket(portNumEnvoie);
				String message = "etat: 1 servPort: "+portNumReception+" tcp: "+manager.getNumPortTcp()+"pseudo: "+pseudo+" final";
				for (int i=65534; i>65233;i--)
				{
					if(i != portNumReception)
					{
					
						broadcast(message,adress,i,envoie);
						//System.out.println("Envoie au port : "+i);

					}
				}
				envoie.close();
			}
			catch(SocketException e)
			{
				System.out.println("Probleme socket udp premier envoie");
			}
			
			
		}
		catch (IOException e)
		{
			System.out.println("Première connexion erreur udp io");
		}
	}

	public void deconnexion (String pseudo)
	{
		//On envoie en broadcast le changement de pseudo a tous les utilisateurs 
		String message = "etat: 3 servPort: "+portNumReception+"pseudo: "+pseudo+" final";
		try {
			DatagramSocket envoie = new DatagramSocket(portNumEnvoie);
			for (int i=65335; i>65233;i--)
			{
				if(i != portNumReception)
				{
			
					broadcast(message,adress,i,envoie);

				}
			}
			envoie.close();
		}
		catch (IOException e)
		{
			System.out.println("Probleme a lenvoi du nouveau login");
		}
			
	}


}


