package network;
import java.net.*;
import java.io.*;

import controller.NetworkManager;
import model.Contact;
import model.Message;



public class ServerTCPThread implements Runnable {
	
	/*----------------------------------------------------- Variable ----------------------------------------*/

	/*socket use for the connection*/
	private final Socket link;
	/*the class which manage connection*/
	private final NetworkManager manager;
	
	/*----------------------------------------------------- Constructor  ----------------------------------------*/

	public ServerTCPThread(Socket link, NetworkManager networkManager) {
		this.link = link;
		this.manager = networkManager;

	}

	/*-----------------------------------------------------Function Run  ----------------------------------------*/

	public void run()
	{
		try 
		{
			BufferedReader in= new BufferedReader (new InputStreamReader (link.getInputStream()));
			String msg ;
			try {
				try{
					//While our contact is connected
					while((msg = in.readLine())!=null)
					{
						System.out.println("en attente");
						int x = msg.indexOf("ZQZQZ");
						String pseudo="";
						String text = "";
						System.out.println(msg);

						/*Looking for the string "ZQZQZ", after that, there is the pseudo of our contact*/
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
						/*Looking the pseudo in the user list of connected people*/
						for (Contact c : manager.getconnectedUser())
						{
							if (c.getPseudo().equals(pseudo))
							{
								System.out.println("courgette");
								manager.getAgent().newMessageReceived(c, text);
							}
						}
					}
				}
				catch(SocketException e)
				{
					//if our contact disconnect, we close our socket
					in.close();
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
	
	/*-----------------------------------------------------Others Function  ----------------------------------------*/

	public void send (Message message)
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
