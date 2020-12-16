import java.net.*;
import java.io.*;

/*Serveur en attente d'une connexion de quelqu'un a notre client */

public class NetworkWaiter implements Runnable {

	private final Socket link;
	private final NetworkManager networkManager;
	private PrintWriter out;
	
	private Contact user;


	public NetworkWaiter(Socket link, NetworkManager networkManager, InetAddress adresse) {
		this.link = link;
		this.networkManager = networkManager;
		try{
			out = new PrintWriter(link.getOutputStream(),true);
		}
		catch(IOException e)
		{
			System.out.println("Erreur création des bufffers, serveur tcp");
		}

		/*On récupère le contact associé à l'adresse*/
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
		System.out.println("Thread du serveur lance");
		try 
		{
			BufferedReader in= new BufferedReader (new InputStreamReader (link.getInputStream()));
			String msg ;
			try {
				msg = in.readLine();
				System.out.println("Message : "+msg);
				//tant que le client est connecte
				while(msg!=null)
				{
					System.out.println("ON a quand meme recu un truc");
					msg = in.readLine();
					System.out.println(msg);
					Message m = new Message(msg,java.time.LocalDate.now(),user);
					networkManager.setReceiveMessage(m);
				}
				//sortir de la boucle si le client a deconecte
				System.out.println("Client serveur deconecte");
				//fermer le flux et la session socket
				out.close();
				link.close();
				} 
			catch (IOException e) 
			{
				System.out.println("probleme dans recevoir");
			}
			

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
		System.out.println("Envoie de : " + msg);
	}


}
