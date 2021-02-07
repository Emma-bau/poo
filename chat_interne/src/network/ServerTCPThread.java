package network;
import java.net.*;
import java.io.*;

import controller.NetworkManager;
import model.Contact;
import model.Message;

/*Serveur en attente d'une connexion de quelqu'un a notre client */

public class ServerTCPThread implements Runnable {

	private final Socket link;
	private final NetworkManager manager;

	public ServerTCPThread(Socket link, NetworkManager networkManager) {
		this.link = link;
		this.manager = networkManager;

	}

	@Override
	public void run()
	{
		try 
		{
			BufferedReader in= new BufferedReader (new InputStreamReader (link.getInputStream()));
			String msg ;
			try {
				try{//tant que le client est connecte
					while((msg = in.readLine())!=null)
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
					}

				}
				catch(SocketException e)
				{
					//sortir de la boucle si le client a deconecte
					System.out.println("Client serveur deconnecte");
					in.close();
					//fermer le flux et la session socket
					link.close();
				}

			} 
			catch (IOException e) 
			{
				System.err.println(e);
				e.printStackTrace();
			}


		}

		catch(Exception e)
		{
			System.err.println(e);
			e.printStackTrace();
		}




	}

	public void envoie (Message message)
	{
		try{
			try
			{
				PrintWriter out = new PrintWriter(link.getOutputStream());
				String msg;
				msg = message.getMessage();
				out.println(msg);
				out.flush();
			}
			catch(SocketException e)
			{
				System.err.println(e);
			}
		}
		catch(IOException e)
		{
			System.err.println(e);
		}
	}



}
