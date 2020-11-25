import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*Serveur en attente d'une connexion de quelqu'un à notre client */

public class NetworkWaiter implements Runnable {
	
	
	public NetworkWaiter() {}
	
	/*On gère la connexion de nos nouveaux utilisateurs dans un thread*/
	@Override
	public void run()
	{
		System.out.println("Thread lancé");
		Socket SocketTCP;
		try {
			
			/*On se met en écoute tant que la session est ouverte*/
			while(true)
			{
				
			}
			System.out.println("Session finie");
			
		
		}
		
		catch (Exception e )
		{
			e.printStackTrace();
		}
		
	}
	
	

}
