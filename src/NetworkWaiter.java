import java.net.*;
import java.io.*;


/*Serveur en attente d'une connexion de quelqu'un � notre client */

public class NetworkWaiter implements Runnable {
	
	private final Socket link;
	private final NetworkManager networkManager;
	
	public NetworkWaiter(Socket link, NetworkManager networkManager) 
	{
		this.link = link;
		this.networkManager = networkManager;
	}
	
	/*On g�re la connexion de nos nouveaux utilisateurs dans un thread*/
	@Override
	public void run()
	{
		System.out.println("Thread lanc�");
		try 
		{
			boolean connexion = true; 
			/*On se met en �coute tant que la session est ouverte*/
			while(connexion)
			{
				String recieve = networkManager.getRecieveMessage();
				String send = networkManager.getSendMessage();
				/*si on re�oit un message*/
				if( recieve != "" )
				{
					BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));					
					String input = in.readLine();
					/*On a besoin d'une fonction qui re�oit ce message et l'envoit � l'interface*/
					System.out.println("Received : "+input);
					networkManager.setRecieveMessage("");
				}
				/*Si on a un message � envoyer*/
				if (send != "")
				{
					PrintWriter out = new PrintWriter(link.getOutputStream(),true);
					/*Fonction du message envoy� voulu*/
					out.println("Courgette");
					networkManager.setSendMessage("");	
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
