import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
			/*On se met en écoute tant que la session est ouverte*/
			while(connexion)
			{
				String rec = NetworkManager.getRecieveMessage();
				if( rec != "" )
				in = link.getInputStream();
				int rcv = 0;
				while ((rcv = in.read()) != 0)
				{
					/*On a besoin d'une fonction qui reçoit ce message et l'envoit à l'interface*/
					System.out.println("Received : "+rcv);
				}
				
				
			}
			System.out.println("Session finie");
			in.close();
			
			
			
		
		}
		
		catch (Exception e )
		{
			e.printStackTrace();
		}
		
	}
	
	

}
