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
			/*On se met en �coute tant que la session est ouverte,truover comment g�rer la connexion*/
			while(connexion)
			{
				String send = networkManager.getSendMessage();
				/*Si on a un message � envoyer*/
				if (send != "")
				{
					PrintWriter out = new PrintWriter(link.getOutputStream(),true);
					/*Fonction du message envoy� voulu*/
					out.println(networkManager.getSendMessage());
					networkManager.setSendMessage("");	
				}
				
				BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
				/*ou si on re�oit un message, le buffer read_line n'est pas vide*/
				if(in.ready())
				{					
					String input = in.readLine();
					/*Network r�cup�re notre message*/
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
