import java.net.*;
import java.io.*;


/*Serveur en attente d'une connexion de quelqu'un à notre client */

public class NetworkWaiter implements Runnable {
	
	private final Socket link;
	private final NetworkManager networkManager;
	
	public NetworkWaiter(Socket link, NetworkManager networkManager) 
	{
		this.link = link;
		this.networkManager = networkManager;
	}
	
	/*On gère la connexion de nos nouveaux utilisateurs dans un thread*/
	@Override
	public void run()
	{
		System.out.println("Thread lancé");
		try 
		{
			boolean connexion = true; 
			/*On se met en écoute tant que la session est ouverte,truover comment gérer la connexion*/
			while(connexion)
			{
				String send = networkManager.getSendMessage();
				/*Si on a un message à envoyer*/
				if (send != "")
				{
					PrintWriter out = new PrintWriter(link.getOutputStream(),true);
					/*Fonction du message envoyé voulu*/
					out.println(networkManager.getSendMessage());
					networkManager.setSendMessage("");	
				}
				
				BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
				/*ou si on reçoit un message, le buffer read_line n'est pas vide*/
				if(in.ready())
				{					
					String input = in.readLine();
					/*Network récupère notre message*/
					System.out.println("Received : "+input);
					networkManager.setRecieveMessage(input);	
				}
				
				
				
				
				
			}
			System.out.println("Session finie");
			link.close();
			
			
			
		
		}
		
		catch (Exception e )
		{
			e.printStackTrace();
		}
		
	}
	
	

}
