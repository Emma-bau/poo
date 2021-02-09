package network;
import java.net.*;

import controller.NetworkManager;


public class ServerTCPHandler extends Thread{
	
	/*----------------------------------------------------- Variable ----------------------------------------*/

	/*the class which manage connection*/
	private NetworkManager manager;
	/*num port use for the connection*/
	private int numPortserv; 
	
	/*----------------------------------------------------- Constructor ----------------------------------------*/

    public ServerTCPHandler (NetworkManager networkManager, int numPortserv )
    {
		this.manager=networkManager;
		this.numPortserv= numPortserv;
    }


    /*----------------------------------------------------- Function Run ----------------------------------------*/
    public void run () 
	{
		try 
		{
			/*Creation of our server*/
			ServerSocket server = new ServerSocket(numPortserv);
			/*While we are connected, we listen*/
			while(manager.isConnexion())
			{
				Socket SocketTCP = server.accept();
				int num = manager.getNumWaiter();
				manager.getConnectedNetwork().add(num,new ServerTCPThread(SocketTCP, manager));
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
