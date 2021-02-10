package network;
import java.io.*;
import java.net.*;

import controller.NetworkManager;
import model.Contact;
import model.Message;



public class ClientTCPHandler extends Thread {

	
	/*----------------------------------------------------- Variable ----------------------------------------*/
	/*the class which manage connection*/
	private final NetworkManager manager;
	/*Our contact*/
	private final Contact user;
	/*socket use for the connection*/
	private Socket clientSocket;
	
	
	/*----------------------------------------------------- Constructor ----------------------------------------*/
	public ClientTCPHandler(NetworkManager Manager, Contact contact)
	{
		this.manager = Manager;
		this.user = contact;
		try
		{
			/*opening the socket with an address and a given port*/
			clientSocket = new Socket(user.getAdresse(),user.getTcp_serv_port());
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	
	/*----------------------------------------------------- Function Run  ----------------------------------------*/
	public void run()
	{
		try {
			BufferedReader in= new BufferedReader (new InputStreamReader (clientSocket.getInputStream()));
			String msg ;
			try{
				//While our contact is connected
				while((msg = in.readLine())!=null)
				{
					int x = msg.indexOf("ZQZQZ");
					String pseudo="";
					String text = "";
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
							manager.getAgent().newMessageReceived(c, text);
						}
					}
				}
			}
			catch(SocketException e)
			{
				//if our contact disconnect, we close our socket
				in.close();
				clientSocket.close();
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	/*----------------------------------------------------- Other Functions ----------------------------------------*/

	/*To send a message to a contact*/
	public void send (Message message)
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
				e.printStackTrace();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	 
	 
	/*To print the connection*/
	public void print()
	{
		System.out.print ("Contact with : ");
		user.print();;
	}
}
