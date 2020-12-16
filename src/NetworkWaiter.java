import java.net.*;
import java.io.*;
import java.util.*;

/*Serveur en attente d'une connexion de quelqu'un a notre client */

public class NetworkWaiter implements Runnable {

	private final Socket link;
	private final NetworkManager networkManager;
	private final InetAddress adresse;
	private PrintWriter out;
	private BufferedReader in;


	public NetworkWaiter(Socket link, NetworkManager networkManager, InetAddress adresse) {
		this.link = link;
		this.networkManager = networkManager;
		this.adresse=adresse;
		try{
			out = new PrintWriter(link.getOutputStream(),true);
			in = new BufferedReader (new InputStreamReader (link.getInputStream()));
		}
		catch(IOException e)
		{
			System.out.println("Erreur création des bufffers, serveur tcp");
		}
	}



	@Override
	public void run()
	{
		System.out.println("Thread du serveur lance");
		try 
		{

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
	public void envoie (Message message)
	{
		String msg;
		msg = message.getMessage();
		out.println(msg);
	}


}
