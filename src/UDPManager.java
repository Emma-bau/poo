import java.io.*;
import java.net.*;
import java.util.*;


public class UDPManager extends Thread{
	
	private int portNumReception; //= 65534;
	private int portNumEnvoie; //= 65535;
	private DatagramSocket udpSocket;
	private InetAddress adress;

	public UDPManager(int numPort) throws SocketException
	{
		//Port de broadcast de tous les utilisateurs : 65535 pour envoyer
		//65534 pour recevoir
		this.portNumReception=numPort;
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
			for (int i=65335; i>65235;i++)
			{
				broadcast("Hello",adress , portNumReception);
			}
		}
		catch(IOException e )
		{
			System.out.println("Erreur envoie du message en broadcast");
		}
		//Création de notre serveur UDP en écoute sur le port 65534
		try{
			DatagramSocket dgramSocket = new DatagramSocket(portNumReception);
			byte[] buffer = new byte[256];
			DatagramPacket inPacket = new DatagramPacket(buffer,buffer.length);
			try
			{

				dgramSocket.receive(inPacket);
				//Réception de l'adresse et du port associé//
				InetAddress clientAddress = inPacket.getAddress();
				int clientPort= inPacket.getPort();
				//On met à jour notre tableau//
			}	
			catch(IOException e)
			{
				System.out.println("Erreur au niveau du receive du Datagramme");
			}
		}
		catch(SocketException e)
		{
			System.out.println("Erreur création du datagramme");
		}
		
		
		
		
		




		
		
	}


	public void update_contact(InetAddress clientAddress, int clientPort, String login)
	{
		
	}
 	
 	
 	

 	
	
	
	
	

}
