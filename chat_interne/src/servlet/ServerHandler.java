package servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Agent;
import model.Contact;

public class ServerHandler extends Thread{
	
	/*-----------------------------------------------------Variable ----------------------------------------*/

	private Agent agent;
	
	/*-----------------------------------------------------Constructor ----------------------------------------*/

	public ServerHandler(Agent agent) {
		this.agent = agent;
		start();
	}
	
	/*-----------------------------------------------------Function Run ----------------------------------------*/
	
	public void run() {
		while(true) {
			loadServer();	
			try {
				Thread.sleep(1000);
			}
			catch(InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	/*-----------------------------------------------------Others Functions----------------------------------------*/

	/*Notificfy serveur of a new state*/
	public void notifyServer(int etat)
	{
		String url = "https://srv-gei-tomcat.insa-toulouse.fr/Server_Jacques_Baudoint/servlet";
		try
		{
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("ID",Integer.toString(agent.getSelf().getId()));
			connection.setRequestProperty("pseudo",agent.getSelf().getPseudo());
			connection.setRequestProperty("adresse",agent.getSelf().getAdresse().getHostAddress());
			connection.setRequestProperty("tcp",Integer.toString(agent.getSelf().getTcp_serv_port()));
			/*if we are in the company*/
			if (agent.isInterne())
			{
				connection.setRequestProperty("status","1");
			}
			else
			{
				connection.setRequestProperty("status","0");
			}

			if (etat == 0) /*disconnection*/
				connection.setRequestProperty("etat", "0");
			else if (etat==1) /*connection*/
				connection.setRequestProperty("etat", "1");
			else if (etat==2) /*change pseudo*/
				connection.setRequestProperty("etat", "2");
			else
				System.out.println("Problem in choice of state for the servlet");	
			@SuppressWarnings("unused")
			BufferedReader in= new BufferedReader (new InputStreamReader (connection.getInputStream()));
			connection.disconnect();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/*Load of distant users*/
	public void loadServer()
	{
		String url = "https://srv-gei-tomcat.insa-toulouse.fr/Server_Jacques_Baudoint/servlet";
		String msg ;
		try
		{
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("GET");
			if(connection.getResponseCode()==200)
			{
				BufferedReader in= new BufferedReader (new InputStreamReader (connection.getInputStream()));
				
				/*Recovery of information by the buffer*/
				while((msg=in.readLine())!=null)
				{
					String id_String =  regexSearch("(?<=id: )\\d+", msg);
					String pseudo = regexSearch("(?<=pseudo: )\\S+", msg);
					String adresse_string = regexSearch("(?<=adresse: )\\S+", msg);
					String servPortTCP_String =  regexSearch("(?<=tcp: )\\d+", msg);
					String statut_String = regexSearch("(?<=statut: )\\d+", msg);
					String state_String = regexSearch("(?<=etat: )\\d+", msg);
					int tcpserv = Integer.parseInt(servPortTCP_String);
					int id = Integer.parseInt(id_String);
					InetAddress adresse =  InetAddress.getByName(adresse_string);

					/*we look at the contact only if it is an extern user*/
					if (!statut_String.equals("1"))
					{

						/*Check if he is already in our list*/
						for(Iterator<Contact> it =  agent.getNetworkManager().getconnectedUser().iterator();it.hasNext();)
						{
							Contact c = (Contact)it.next();
							if(c.getId() == id)
							{
								it.remove();
							}
						}	
						/*Check if it is not ourself or if it is not a disconnected contact*/
						if (id!=agent.getSelf().getId()&&!(state_String.equals("0")))
						{
							agent.getNetworkManager().getconnectedUser().add(new Contact(tcpserv,pseudo,id,adresse));
						}
					}
				}
				connection.disconnect();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*Use to search information in a string*/
	public static String regexSearch(String regex, String input) {
		Matcher m = Pattern.compile(regex).matcher(input);
		if (m.find()) return m.group();
		return null;
	}

}
