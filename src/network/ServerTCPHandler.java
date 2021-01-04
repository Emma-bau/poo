package network;
import java.net.*;

import controller.NetworkManager;


public class ServerTCPHandler extends Thread{

	private NetworkManager manager;
	private int numPortserv; 

    public ServerTCPHandler (NetworkManager networkManager, int numPortserv )
    {
		this.manager=networkManager;
		this.numPortserv= numPortserv;
    }


    public void run () 
	{
		try 
		{
			/*Creation de notre serveur locale d'ecoute*/
			ServerSocket server = new ServerSocket(numPortserv);
			/*On se met en ecoute tant que la session est ouverte*/
			while(manager.isConnexion())
			{
				/*Attente de connexion*/
				Socket SocketTCP = server.accept();
				int num = manager.getNumWaiter();
				manager.getConnectedNetwork().add(num,new ServerTCPThread(SocketTCP, manager, SocketTCP.getInetAddress()));
				Thread t1 = new Thread(manager.getConnectedNetwork().get(num));
				t1.start();
				manager.setNumWaiter(num++);
			}
			server.close();
		}
		
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
		
	}


    
}
