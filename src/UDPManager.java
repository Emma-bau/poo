import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class UDPManager extends Thread{
	
	private int portNumReception; //= 65534;
	private int portNumEnvoie = 65335; 
	private InetAddress adress;
	private NetworkManager manager;

	private static final int CHANGE_LOGIN = 0;
	private static final int CONNEXION = 1;
	private static final int DECONNEXION= 2;
	private static final int ANSWER_CONNEXION = 3;



	public UDPManager(int numPort, NetworkManager net ) throws SocketException
	{
		//Port de broadcast de tous les utilisateurs : 65535 pour envoyer
		//65534 pour recevoir
		this.portNumReception=numPort;
		this.manager=net;
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
		
	//Création de notre serveur UDP en écoute sur le port 65534
		try{
			//Création du port de réception
			DatagramSocket dgramSocketReception = new DatagramSocket(portNumReception);
			byte[] buffer = new byte[256];
			DatagramPacket inPacket = new DatagramPacket(buffer,buffer.length);

			Thread serveur = new Thread(new Runnable()
			{
				public void run()
				{
					try{
						System.out.println("Serveur créer");
						while(manager.isConnexion())
						{
							dgramSocketReception.receive(inPacket);
							//Réception de l'adresse et du port associé//
							InetAddress clientAddress = inPacket.getAddress();
							
							//Récupération des informations du message						
							String input="";
							for(int i=0; i<buffer.length; i++)
							{
								input += (char)buffer[i];
							}

							String etat_String = regexSearch("(?<=etat: )\\d+", input);
							String servPort_String = regexSearch("(?<=servPort: )\\d+", input);
							String pseudo = regexSearch("(?<=pseudo: )\\S+", input);

							int etat = Integer.parseInt(etat_String);
							int servPort= Integer.parseInt(servPort_String);

							//Changement de login//
							if(etat==CHANGE_LOGIN)
							{
								update_contact(clientAddress,pseudo);
								//Changer le pseudo à envoyer à l'interface//
							}
							//Nouvelle Connexion
							else if(etat==CONNEXION || etat==ANSWER_CONNEXION )
							{
								create_contact(clientAddress,pseudo,servPort,etat);

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
				
			});
			serveur.start();

		}
		catch(IOException e )
		{
			System.out.println("Erreur UDP socket");
		}

		//Envoie de la connexion de notre client
		Thread connexion = new Thread(new Runnable()
		{
			public void run()
			{
				try
				{
					adress = InetAddress.getByName("localhost");
				}
				catch(UnknownHostException e)
				{
					System.out.println("Erreur dans le broadcast, hote inconnu");
				}
				try
				{
					System.out.println("Connexion envoyée");
					DatagramSocket envoie = new DatagramSocket(portNumEnvoie);
					String message = "etat: 1 servPort: "+portNumReception+"pseudo: "+manager.getPseudo();
					for (int i=65333; i>65233;i--)
					{
						if(i != portNumReception)
						{
							
							broadcast(message,adress,i,envoie);

						}
					}
					envoie.close();
				}
				catch(IOException e )
				{
					System.out.println("Erreur envoie du message en broadcast");
				}
			}
		});
		connexion.start();
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

	public void create_contact(InetAddress clientAddress, String pseudo, int ServPort, int etat)
	{

		try{
			//Mise à jur de nos contacts//
			ArrayList<Contact> connectedUser = manager.getconnectedUser();

			Contact C = new Contact(ServPort,pseudo,clientAddress);
			connectedUser.add(C);
			manager.setconnectedUser(connectedUser);

			//Vérification console//
			for (Contact c :  manager.getconnectedUser())
			{
				c.afficher();
			}

			//Si c'est une première connexion alors on répond, sinon c'est une réponse à notre premier envoie	
			if (etat == 1)
			{
				String message="etat: 3 servPort: "+portNumReception+"pseudo: "+manager.getPseudo();
				byte [] buffer = message.getBytes();
				try
				{
					//Envoie de nos informations
					System.out.println("Envoie du message de connexion");
					DatagramSocket envoie = new DatagramSocket(portNumEnvoie);
					DatagramPacket packet = new DatagramPacket (buffer, buffer.length, clientAddress, ServPort);
					envoie.send(packet);
					envoie.close();
				}
				catch(SocketException e)
				{
					System.out.println("Erreur message dans la réponse à une connexion");
				}
			}

		}
		catch(IOException e)
		{
			System.out.println("Erreur message dans la réponse à une connexion");
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


}
