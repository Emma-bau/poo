import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*Serveur en attente d'une connexion de quelqu'un � notre client */

public class NetworkWaiter implements Runnable {
	
	
	public NetworkWaiter() {}
	
	/*On g�re la connexion de nos nouveaux utilisateurs dans un thread*/
	@Override
	public void run()
	{
		System.out.println("Thread lanc�");
		Socket SocketTCP;
		try {
			
			/*On se met en �coute tant que la session est ouverte*/
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
