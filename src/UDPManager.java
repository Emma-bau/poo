import java.io.*;
import java.net.*;
import java.util.*;


public class UDPManager extends Thread{
	
	private int portNum;
	private NetworkManager networkManager;

	
	/* Statut de connexion*/
	public static final int NONE_STATUS = -1;
	public static final int CONNECTION_STATUS = 0;
	public static final int DECONNECTION_STATUS = 1;
	public static final int USERNAME_CHANGED_OCCUPIED = 2;
	
	public UDPManager() throws SocketException
	{
		//Choix d'un port random pour le nouveau USER, compris entre 2000 et 65500//
		Random rand = new Random();
		this.portNum = 1999;
				/*rand.nextInt(65500-2000+1)+2000*/ ;
		networkManager.setNumPort(portNum);
		
		
		//this.udpSocket = new DatagramSocket(portNum);
		
		
		
	}
	
	
	public void broadcast(byte [] message, InetAddress address, int portNum) throws IOException
	{
		//udpSocket.setBroadcast(true);
		DatagramPacket packet = new DatagramPacket (message, message.length, address, portNum);
		//udpSocket.send(packet);
		
	}
 	
 	
 	

 	
	
	
	
	

}
