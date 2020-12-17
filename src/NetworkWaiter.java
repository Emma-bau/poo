import java.net.*;
import java.io.*;

/*Serveur en attente d'une connexion de quelqu'un a notre client */

public class NetworkWaiter implements Runnable {

	private final Socket link;
	private final NetworkManager networkManager;
	
	private Contact user;


	public NetworkWaiter(Socket link, NetworkManager networkManager, InetAddress adresse) {
		this.link = link;
		this.networkManager = networkManager;
		for (Contact C : networkManager.getconnectedUser())
		{
			if (C.getAdresse() == adresse)
			{
				user = C;
			}
		}

	}



	@Override
	public void run()
	{
		try 
		{
			BufferedReader in= new BufferedReader (new InputStreamReader (link.getInputStream()));
			String msg ;
			try {
				msg = in.readLine();
				//tant que le client est connecte
				while(msg!=null)
				{
					System.out.println("Message : "+msg);
					networkManager.getAgent().newMessageReceived(user, msg);
					msg = in.readLine();
				}
				//sortir de la boucle si le client a deconecte
				System.out.println("Client serveur deconnecte");
				//fermer le flux et la session socket
				link.close();
				} 
			catch (IOException e) 
			{
				e.printStackTrace();
				System.out.println("probleme dans recevoir");
			}
			

		}

		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Erreur au niveau du serveur niveau 2");
		}

	


	}

	public void envoie (Message message)
	{
		try{
			try
			{
				PrintWriter out = new PrintWriter(link.getOutputStream());
				String msg;
				msg = message.getMessage();
				System.out.println("Envoie de : " + msg);
				out.println(msg);
				out.flush();
			}
			catch(SocketException e)
			{
				System.out.println("Erreur ouverture buffer en out dans clienthandler");
			}
		}
		catch(IOException e)
		{
			System.out.println("Erreur ouverture buffer en out dans clienthandler");
		}
	}


}
