import java.net.*;
import java.io.*;
import java.util.*;

/*Serveur en attente d'une connexion de quelqu'un a notre client */

public class NetworkWaiter implements Runnable {

	private final Socket link;
	private final NetworkManager networkManager;
	private final InetAddress adresse;

	public NetworkWaiter(Socket link, NetworkManager networkManager, InetAddress adresse) {
		this.link = link;
		this.networkManager = networkManager;
		this.adresse=adresse;
	}

	/* On gere la connexion de nos nouveaux utilisateurs dans un thread */
	/*Reste a gerer avec l'id a qui on envoit*/
	@Override
	public void run()
	{
		System.out.println("Thread du serveur lance");
		try 
		{
			PrintWriter out = new PrintWriter(link.getOutputStream(),true);
			BufferedReader in = new BufferedReader (new InputStreamReader (link.getInputStream()));
			/*Mise en place d'un thread pour l'envoie des messages*/
			Thread envoie = new Thread(new Runnable() {
				String msg;
				@Override
				public void run() {
					while(true)
					{
							//On envoie le message si l'adresse correspond à l'adresse de notre client
							if ((networkManager.getSendMessage() != null ) && (networkManager.getSendMessage().getContact().getAdresse()==adresse))
							{
								msg = networkManager.getSendMessage().getMessage();
								out.println(msg);
								out.flush();
							}

					}
				}
			});
			envoie.start();

			/*Mise en place d'un thread pour recevoir les messages*/
			Thread recevoir= new Thread(new Runnable() {
				String msg ;
				Contact client;
				@Override
				public void run() {
					try {
						msg = in.readLine();
						//tant que le client est connecte
						while(msg!=null)
						{
							msg = in.readLine();
							//revoir id source ? 
							for (Contact C : networkManager.getconnectedUser())
							{
								if (C.getAdresse() == adresse)
								{
									client = C;
								}
							}
							Message m = new Message(msg,java.time.LocalDate.now(),client);
							networkManager.setReceiveMessage(m);
						}
						//sortir de la boucle si le client a deconecte
						System.out.println("Client serveur deconecte");
						//fermer le flux et la session socket
						out.close();
						link.close();
						} 
					catch (IOException e) {
							System.out.println("probleme dans recevoir");
						}
				}
			});
		recevoir.start();
				
	}

	catch(Exception e)
	{
		System.out.println("Erreur au niveau du serveur niveau 2");
	}

}

}
