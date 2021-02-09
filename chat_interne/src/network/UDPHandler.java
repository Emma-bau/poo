package network;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.NetworkManager;
import model.Contact;




public class UDPHandler extends Thread{
	
	/*-----------------------------------------------------Variable ----------------------------------------*/
	
	/*The definition of the range is used because we need to test our application in one pc*/
	// define the range of reception socket
    int max = 65500; 
    int min = 64500; 
	int range = max - min + 1; 
	private int portNumReception ;
	
	// define the range of send socket
    int max1 = 64499; 
    int min1 = 63499; 
	int range1 = max1 - min1 + 1; 
	private int portNumSend; 
	
	/*Address of broadcast*/
	private InetAddress adress;
	/*manager of connection*/
	private NetworkManager manager;

	/*Static variable for state of user*/
	private static final int CHANGE_LOGIN = 0;
	private static final int CONNEXION = 1;
	private static final int DECONNEXION= 2;
	private static final int ANSWER_CONNEXION = 3;

	
	/*----------------------------------------------------- Constructor ----------------------------------------*/

	public UDPHandler(NetworkManager net ) throws SocketException
	{
		this.manager=net;
		/*The port num is randomly assigned*/
		portNumReception =  (int)(Math.random() * range) + min;
		portNumSend = (int)(Math.random() * range1) + min1;
	}
	

	/*----------------------------------------------------- Function Run ----------------------------------------*/
	public void run()
	{
		try
		{
			/*Creation of our sever UDP*/
			DatagramSocket dgramSocketReception = new DatagramSocket(portNumReception);
			byte[] buffer = new byte[256];
			DatagramPacket inPacket = new DatagramPacket(buffer,buffer.length);
			try{
				System.out.println("Serveur UDP created");	
				while(manager.isConnexion())
				{
					dgramSocketReception.receive(inPacket);
					//Reception of adress
					InetAddress clientAddress = inPacket.getAddress();
					//Recovery of the message					
					String input="";
					for(int i=0; i<buffer.length; i++)
					{
						input += (char)buffer[i];
					}
					System.out.println(input);
					String state_String = regexSearch("(?<=etat: )\\d+", input);
					String servPortUDP_String = regexSearch("(?<=servPort: )\\d+", input);
					String servPortTCP_String =  regexSearch("(?<=tcp: )\\d+", input);
					String id_String =  regexSearch("(?<=id: )\\d+", input);
					String pseudo = regexSearch("(?<=pseudo: )\\S+", input);			
					int state = Integer.parseInt(state_String);
					int udpserv= Integer.parseInt(servPortUDP_String);
					int tcpserv = Integer.parseInt(servPortTCP_String);
					int id = Integer.parseInt(id_String);
					

					//Change of login//
					if(state==CHANGE_LOGIN)
					{
						update_contact(clientAddress,pseudo);
					}
					//New connection
					else if(state==CONNEXION || state==ANSWER_CONNEXION )
					{
						create_contact(clientAddress,pseudo,udpserv,state,tcpserv,id);

					}
					//Deconnection
					else if(state==DECONNEXION)
					{
						remove_contact(clientAddress,pseudo);
					}
					else       
					{
						System.out.println("Error in broadcast, no correspondence with cases");
					}	
				}
				dgramSocketReception.close();			
			}
			catch(IOException e )
			{
				e.printStackTrace();
				System.out.println("Thread UDP socket");
			}
		}
		catch(SocketException e)
		{
			e.printStackTrace();
		}
	}
	
	/*----------------------------------------------------- Others Functions----------------------------------------*/	

	/*broadcast our state*/
	public void broadcast(String message, InetAddress address, int portNum) throws IOException
	{
		DatagramSocket envoie = new DatagramSocket(portNumSend);
		envoie.setBroadcast(true);
		byte [] buffer = message.getBytes();
		DatagramPacket packet = new DatagramPacket (buffer, buffer.length, address, portNum);
		envoie.send(packet);	
		envoie.close();
	}

	/*update a contact of the list with new information*/ 
	public void update_contact(InetAddress clientAddress, String pseudo)
	{
		ArrayList<Contact> connectedUser = manager.getconnectedUser();
		for (Contact c : connectedUser)
		{
			if(c.getAdresse() == clientAddress)
			{
				c.setPseudo(pseudo);
			}
		}
		manager.setconnectedUser(connectedUser);	
	}

	/*Create a new contact on our connected user list, 
	 *if it is someone new in the network, we send a respond to indicate that we are present too*/
	public void create_contact(InetAddress clientAddress, String pseudo, int ServPort, int etat, int tcp, int id)
	{
		try{
			//Update//
			ArrayList<Contact> connectedUser = manager.getconnectedUser();
			Contact C = new Contact(ServPort,tcp,pseudo,clientAddress,id);
			connectedUser.add(C);
			manager.setconnectedUser(connectedUser);

			//Check on console//
			for (Contact c :  manager.getconnectedUser())
			{
				c.print();
			}

			//if it is a first connection we send a respond	
			if (etat == 1)
			{
				String message="etat: 3 servPort: "+portNumReception+"tcp: "+manager.getNumPortTcp()+"id: "+manager.getAgent().getSelf().getId()+"pseudo: "+manager.getAgent().getPseudoManager().getPseudo()+" final";
				byte [] buffer = message.getBytes();
				try
				{
					DatagramSocket envoie = new DatagramSocket(portNumSend);
					DatagramPacket packet = new DatagramPacket (buffer, buffer.length, clientAddress, ServPort);
					envoie.send(packet);
					envoie.close();
				}
				catch(SocketException e)
				{
					e.printStackTrace();
				}
			}

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}	
	}

	/*remove a contact of our connected user list*/
	public void remove_contact(InetAddress clientAddress, String pseudo)
	{
		ArrayList<Contact> connectedUser = manager.getconnectedUser();
		for(Iterator<Contact> it = connectedUser.iterator();it.hasNext();)
		{
			Contact c = (Contact)it.next();
			c.print();
			if(c.getAdresse() == clientAddress)
			{
				it.remove();
			}
		}
		manager.setconnectedUser(connectedUser);	
	}

	/*Use to search some information in a string*/
	public static String regexSearch(String regex, String input) {
        Matcher m = Pattern.compile(regex).matcher(input);
        if (m.find()) return m.group();
        return null;
	}
	
	/*Notify everybody that we change our pseudo*/
	public void change_pseudo(String pseudo)
	{ 
		String message = "etat: 0 servPort: "+portNumReception+" tcp: "+manager.getNumPortTcp()+"id: "+manager.getAgent().getSelf().getId()+"pseudo: "+pseudo+" final";
		try {
			for (int i=65500; i>64500;i--)
			{
				if(i != portNumReception)
				{	
					broadcast(message,adress,i);
				}
			}
			/*Notification to the servlet*/
			manager.getAgent().getServerHandler().notifyServer(2);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("Probleme a l'envoi du nouveau pseudo");
		}
	}

	/*Notify everybody of a first connection*/
	public void first_connexion (String pseudo)
	{
		try
		{
			start();
			this.adress =  InetAddress.getByName("255.255.255.255");
			/*Creation of ourself*/
			manager.getAgent().getSelf().setPseudo(pseudo);
			manager.getAgent().getSelf().setTcp_serv_port(manager.getNumPortTcp());
			manager.getAgent().getSelf().setUdp_serv_port(portNumReception);
			manager.getAgent().getSelf().setAdresse(InetAddress.getLocalHost());
		}
		catch(UnknownHostException e)
		{
			e.printStackTrace();
		}
		try{
			try 
			{	/*sending information of our new connection*/
				String message = "etat: 1 servPort: "+portNumReception+" tcp: "+manager.getNumPortTcp()+"id: "+manager.getAgent().getSelf().getId()+"pseudo: "+pseudo+" final";
				for (int i=65500; i>64500;i--)
				{
					if(i != portNumReception)
					{
						broadcast(message,adress,i);
					}
				}
				/*Notification to the servlet*/
				manager.getAgent().getServerHandler().notifyServer(1);
				
			}
			catch(SocketException e)
			{
				e.printStackTrace();
			}
			
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("erreur udp first connexion");
		}
	}

	/*Notify everybody of our deconnection*/
	public void deconnexion (String pseudo)
	{
		String message =  "etat: 2 servPort: "+portNumReception+" tcp: "+manager.getNumPortTcp()+"id: "+manager.getAgent().getSelf().getId()+"pseudo: "+pseudo+" final";
		try {
			for (int i=65500; i>64500;i--)
			{
				if(i != portNumReception)
				{
					broadcast(message,adress,i);
				}
			}
			/*Notify the servlet*/
			manager.getAgent().getServerHandler().notifyServer(0);	
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}		
	}


}


