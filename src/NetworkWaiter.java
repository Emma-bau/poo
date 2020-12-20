import java.net.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*Serveur en attente d'une connexion de quelqu'un a notre client */

public class NetworkWaiter implements Runnable {

	private final Socket link;
	private final NetworkManager networkManager;
	private Contact user;
	


	public NetworkWaiter(Socket link, NetworkManager networkManager, InetAddress adresse) {
		this.link = link;
		this.networkManager = networkManager;

	}



	@Override
	public void run()
	{
		try 
		{
			BufferedReader in= new BufferedReader (new InputStreamReader (link.getInputStream()));
			String msg ;
			try {
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
					for (Contact c : networkManager.getconnectedUser())
					{
						if (c.getPseudo().equals(pseudo))
						{
							networkManager.getAgent().newMessageReceived(c, text);
						}
					}
					
					msg = in.readLine();
				}
				//sortir de la boucle si le client a deconecte
				System.out.println("Client serveur deconnecte");
				//fermer le flux et la session socket
				link.close();
				} 
			catch (IOException e) 
			{
				e.printStackTrace();
				System.out.println("probleme dans recevoir");
			}
			

		}

		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Erreur au niveau du serveur niveau 2");
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
				System.out.println("Erreur ouverture buffer en out dans clienthandler");
			}
		}
		catch(IOException e)
		{
			System.out.println("Erreur ouverture buffer en out dans clienthandler");
		}
	}

	public static String regexSearch(String regex, String input) {
        Matcher m = Pattern.compile(regex).matcher(input);
        if (m.find()) return m.group();
        return null;
	}


}
