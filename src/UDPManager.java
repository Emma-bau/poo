import java.io.*;
import java.net.*;
import java.util.*;


public class UDPManager extends Thread{
	
	private int portNumReception = 65534;
	private int portNumEnvoie = 65535;
	private DatagramSocket udpSocket;
	private InetAddress adress;

	public UDPManager() throws SocketException
	{
		//Port de broadcast de tous les utilisateurs : 65535 pour envoyer
		//65534 pour recevoir
		this.udpSocket = new DatagramSocket(portNumEnvoie);	
	}
	
	
	public void broadcast(String message, InetAddress address, int portNum) throws IOException
	{
		udpSocket.setBroadcast(true);
		byte [] buffer = message.getBytes();
		DatagramPacket packet = new DatagramPacket (buffer, buffer.length, address, portNum);
		udpSocket.send(packet);
		udpSocket.close();
		
	}






	public void run()
	{
		try
		{
			adress = InetAddress.getByName("255.255.255.255");
		}
		catch(UnknownHostException e)
		{
			System.out.println("Erreur dans le broadcast, hote inconnu");
		}
		try
		{
			broadcast("Hello",adress , portNumReception);
		}
		catch(IOException e )
		{
			System.out.println("Erreur envoie du message en broadcast");
		}
		//Création de notre serveur UDP en écoute sur le port 65534
		DatagramSocket dgramSocket = new DatagramSocket()

		
		
	}
 	
 	
 	

 	
	
	
	
	

}
