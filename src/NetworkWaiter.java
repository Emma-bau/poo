import java.net.*;
import java.io.*;
import java.util.*;

/*Serveur en attente d'une connexion de quelqu'un a notre client */

public class NetworkWaiter implements Runnable {

	private final Socket link;
	private final NetworkManager networkManager;
	private final int idClient;

	public NetworkWaiter(Socket link, NetworkManager networkManager, int id_Client) {
		this.link = link;
		this.networkManager = networkManager;
		this.idClient=id_Client;
	}

	/* On gere la connexion de nos nouveaux utilisateurs dans un thread */
	/*Reste a gerer avec l'id a qui on envoit*/
	@Override
	public void run()
	{
		System.out.println("Thread du serveur lance");
		Scanner sc = new Scanner(System.in);
		try 
		{
			PrintWriter out = new PrintWriter(link.getOutputStream(),true);
			BufferedReader in = new BufferedReader (new InputStreamReader (link.getInputStream()));
			/*Mise en place d'un thread pour l'envoie des messages*/
			Thread envoie = new Thread(new Runnable() {
				String msg;
				@Override
				public void run() {
					while(true){
						
							msg = sc.nextLine();
							out.println(msg);
							out.flush();
					}
				}
		});
		envoie.start();

		/*Mise en place d'un thread pour recevoir les messages*/
		Thread recevoir= new Thread(new Runnable() {
			String msg ;
			@Override
			public void run() {
				try {
					msg = in.readLine();
					//tant que le client est connecte
					while(msg!=null){
						System.out.println("Client cote serveur n°"+idClient+": "+msg);
						msg = in.readLine();
						//revoir id source ? 
						Message m = new Message(0, idClient,msg,java.time.LocalDate.now());
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
