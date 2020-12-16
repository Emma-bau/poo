import java.net.*;


public class ServerHandler extends Thread{

	private NetworkManager manager;
	private int numPortserv; 

    public ServerHandler (NetworkManager networkManager, int numPortserv )
    {
		this.manager=networkManager;
		this.numPortserv= numPortserv;
    }


    public void run () 
	{
		try 
		{
            System.out.println("Création sur serveur");
			/*Creation de notre serveur locale d'ecoute*/
			/*Pour tous nos serveurs, le num de port est 2000*/
			ServerSocket server = new ServerSocket(numPortserv);
			/*On se met en ecoute tant que la session est ouverte*/
			while(manager.isConnexion())
			{
				/*Attente de connexion*/
				System.out.println("Awaiting connection");
				Socket SocketTCP = server.accept();
				System.out.println("Connexion d'un client au serveur'");
				int num = manager.getNumWaiter();
				manager.getConnectedNetwork().add(num,new NetworkWaiter(SocketTCP, manager, SocketTCP.getInetAddress()));
				Thread t1 = new Thread(manager.getConnectedNetwork().get(num));
				t1.start();
				manager.setNumWaiter(num++);
			}
			server.close();
		}
		
		catch (Exception e) {
			System.out.println("Erreur au niveau du serveur niveau 1");
		}
		
	}


    
}
