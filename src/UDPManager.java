import java.io.*;
import java.net.*;
import java.util.*;




public class UDPManager extends Thread{
	
	private int portNumReception; //= 65534;
	private int portNumEnvoie = 65335; 
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
						while(true)
						{
							dgramSocketReception.receive(inPacket);
							//Réception de l'adresse et du port associé//
							InetAddress clientAddress = inPacket.getAddress();
							//broadcast numéro port							
							String pseudo="";
							for(int i=1; i<buffer.length; i++)
							{
								pseudo += (char)buffer[i];
							}
							System.out.println("Message : "+pseudo);

							char etat_char = (char)buffer[0];
							int etat = Character.getNumericValue(etat_char);

							//Changement de login//
							if(etat==CHANGE_LOGIN)
							{
								update_contact(clientAddress,pseudo);
								//Changer le pseudo à envoyer à l'interface//
							}
							//Nouvelle Connexion
							else if(etat==CONNEXION)
							{
								create_contact(clientAddress,pseudo);

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
					System.out.println("Attente de lancement");
					Scanner sc = new Scanner(System.in);
					int monEntier = sc.nextInt();
					sc.close();
					DatagramSocket envoie = new DatagramSocket(portNumEnvoie);
					String message = "1"+manager.getPseudo();
					System.out.println(message);
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

	public void create_contact(InetAddress clientAddress, String pseudo)
	{
		System.out.println("Cool arrivée ici");

		ArrayList<Contact> connectedUser = manager.getconnectedUser();

		Contact C = new Contact(2000,pseudo,clientAddress);
		connectedUser.add(C);
		manager.setconnectedUser(connectedUser);

		System.out.println("Client créer : "+2000);
		for (Contact c :  manager.getconnectedUser())
		{
			c.afficher();
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


}
