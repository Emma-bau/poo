import java.net.*;


public class ServerHandler extends Thread{

	private NetworkManager manager;
	private int idClient = 1;

    public ServerHandler (NetworkManager networkManager )
    {
		this.manager=networkManager;
    }


    public void run () 
	{
		try 
		{
            System.out.println("Création sur serveur");
			/*Creation de notre serveur locale d'ecoute*/
			/*Pour tous nos serveurs, le num de port est 2000*/
			ServerSocket server = new ServerSocket(2000);
			/*On se met en ecoute tant que la session est ouverte*/
			while(manager.isConnexion())
			{
				/*Attente de connexion*/
				System.out.println("Awaiting connection");
				Socket SocketTCP = server.accept();
				System.out.println("Connexion du Client " + idClient);
				NetworkWaiter N1 = new NetworkWaiter(SocketTCP, manager, idClient);
				idClient++;
				Thread t1 = new Thread(N1);
				t1.start();
			}
			server.close();
		}
		
		catch (Exception e) {
			System.out.println("Erreur au niveau du serveur niveau 1");
		}
		
	}


    
}
