import java.io.*;
import java.net.*;
import java.util.*;

public class ServerHandler extends Thread{

    private int NumPort;
	private NetworkManager manager;
	private int idClient;

    public ServerHandler ( int numPort, NetworkManager networkManager, int idUser )
    {
		this.NumPort = numPort;
		this.manager=networkManager;
		manager.setNumPort(numPort++);
		this.idClient=idUser;
    }


    public void run () 
	{
		try 
		{
            System.out.println("Création sur serveur");
			/*Creation de notre serveur locale d'ecoute*/
			ServerSocket server = new ServerSocket(NumPort);
			/*On se met en ecoute tant que la session est ouverte*/
			while(true)
			{
				/*Attente de connexion*/
				System.out.println("Awaiting connection");
				Socket SocketTCP = server.accept();
				System.out.println("Connexion du Client " + idClient);
				manager.setIDUser();
				NetworkWaiter N1 = new NetworkWaiter(SocketTCP, manager, idClient);
				Thread t1 = new Thread(N1);
				t1.start();
			}
		}
		
		catch (Exception e) {
			System.out.println("Erreur au niveau du serveur niveau 1");
		}
		
	}


    
}
