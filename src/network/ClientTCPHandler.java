package network;
import java.io.*;
import java.net.*;

import controller.NetworkManager;
import model.Contact;
import model.Message;



public class ClientTCPHandler extends Thread {

	private final NetworkManager manager;
	private final Contact user;
	private Socket clientSocket;

    public ClientTCPHandler(NetworkManager Manager, Contact contact)
    {
		this.manager = Manager;
		this.user = contact;
		try
		{
			clientSocket = new Socket(user.getAdresse(),user.getTcp_serv_port());

		}
		catch(IOException e)
		{
			System.out.println("Erreur dans l'ouverture du socket client");
		}

    }

	public void run()
	{
		try {
			BufferedReader in= new BufferedReader (new InputStreamReader (clientSocket.getInputStream()));
			String msg ;
			msg = in.readLine();
			//tant que le client est connecte
			while(msg!=null)
			{
				int x = msg.indexOf("ZQZQZ");
					String pseudo="";
					String text = "";

					for (int i = 0; i<msg.length(); i++)
					{
						if(i<x)
						{
							text+=msg.charAt(i);
						}
						else if(i>(x+4))
						{
							pseudo +=msg.charAt(i);
						}
					}
					for (Contact c : manager.getconnectedUser())
					{
						if (c.getPseudo().equals(pseudo))
						{
							manager.getAgent().newMessageReceived(c, text);
						}
					}
					
					msg = in.readLine();
			}
			System.out.println("Serveur deconnecte");
			//fermer le flux et la session socket
			clientSocket.close();

		} 
		catch (IOException e) 
		{
			System.out.println("Connection reset (serveur deconnecte)");
		}
	}
	

	public void envoie (Message message)
	{
		try{
			try
			{
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
				String msg;
				msg = message.getMessage()+"ZQZQZ"+manager.getAgent().getPseudoManager().getPseudo();
				out.println(msg);
				out.flush();
			}
			catch(SocketException e)
			{
				System.out.println("Erreur ouverture buffer en out dans clienthandler");
			}
		}
		catch(IOException e)
		{
			System.out.println("Erreur ouverture buffer en out dans clienthandler");
		}
				
				
	}

	public void afficher()
	{
		System.out.print ("Contact avec : ");
		user.afficher();;
	}
}
