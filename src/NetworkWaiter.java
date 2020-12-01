import java.net.*;
import java.io.*;


/*Serveur en attente d'une connexion de quelqu'un a notre client */

public class NetworkWaiter implements Runnable {
	
	private final Socket link;
	private final NetworkManager networkManager;
	
	public NetworkWaiter(Socket link, NetworkManager networkManager) 
	{
		this.link = link;
		this.networkManager = networkManager;
	}
	
	/*On gere la connexion de nos nouveaux utilisateurs dans un thread*/
	@Override
	public void run()
	{
		System.out.println("Thread lance");
		try 
		{
			networkManager.setConnexion(true); 
			/*On se met en ecoute tant que la session est ouverte,trouver comment gerer la connexion*/
			while(networkManager.isConnexion()==true)
			{
				String send = networkManager.getSendMessage();
				/*Si on a un message a envoyer*/
				if (send != "")
				{
					PrintWriter out = new PrintWriter(link.getOutputStream(),true);
					/*Fonction du message envoye voulu*/
					out.println(networkManager.getSendMessage());
					networkManager.setSendMessage("");	
				}
				
				BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
				/*ou si on recoit un message, le buffer read_line n'est pas vide*/
				if(in.ready())
				{					
					String input = in.readLine();
					/*Network recupere notre message*/
					System.out.println("Received : "+input);
					networkManager.setReceiveMessage(input);	
				}
				
				
				
				
				
			}
			System.out.println("Session finie");
			link.close();
			
			
			
		
		}
		
		catch (Exception e )
		{
			System.out.println("Erreur au niveau du serveur niveau 2");
		}
		
	}
	
	

}
