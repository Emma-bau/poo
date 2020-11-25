import java.net.ServerSocket;
import java.net.Socket;

public class NetworkManager extends Thread {

	public void server() 
	{
		try {
			/*Création de notre serveur locale d'écoute*/
			ServerSocket serv = new ServerSocket(1234);
			/*On se met en écoute tant que la session est ouverte*/
			while(true)
			{
				/*Attente de connexion*/
				System.out.println("Awaiting connection");
				Socket SocketTCP = serv.accept();

			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
