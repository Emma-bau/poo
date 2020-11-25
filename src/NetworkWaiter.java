import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*Serveur en attente d'une connexion de quelqu'un � notre client */

public class NetworkWaiter implements Runnable {
	
	final Socket link;
	public NetworkWaiter(Socket link) {this.link = link;}
	
	/*On g�re la connexion de nos nouveaux utilisateurs dans un thread*/
	@Override
	public void run()
	{
		System.out.println("Thread lanc�");
		try {
			/*On se met en �coute tant que la session est ouverte*/
			while(true)
			{
				InputStream in = link.getInputStream();
				
			}
			System.out.println("Session finie");
			
		
		}
		
		catch (Exception e )
		{
			e.printStackTrace();
		}
		
	}
	
	

}
