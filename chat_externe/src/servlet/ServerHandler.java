package servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Agent;
import model.Contact;

public class ServerHandler extends Thread{
	
	/*-----------------------------------------------------Variable ----------------------------------------*/

	private Agent agent;
	
	/*----------------------------------------------------- Constructor ----------------------------------------*/

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
			catch(InterruptedException e) {}
		}
	}
	
	/*----------------------------------------------------- Others Functions ----------------------------------------*/
	
	/*first connection notification*/
	public void first_connexion (String pseudo) throws UnknownHostException
	{
			/*Creation of ourselves*/
			agent.getSelf().setPseudo(pseudo);
			agent.getSelf().setTcp_serv_port(agent.getNetworkManager().getNumPortTcp());
			agent.getSelf().setAdresse(InetAddress.getLocalHost());
			/*send information*/
			notifyServer(1);			
	}

	/*change pseudo*/
	public void change_pseudo (String pseudo)
	{
			notifyServer(2);	
	}
	
	/*disconnection*/
	public void deconnexion (String pseudo)
	{
			notifyServer(0);	
	}


	/*Notify servlet of a change*/
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
				System.out.println("Probleme dans le choix de l'�tat pour le serveur");
			
			@SuppressWarnings("unused")
			BufferedReader in= new BufferedReader (new InputStreamReader (connection.getInputStream()));
			
			connection.disconnect();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/*load information of the servlet*/
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
				
				/*R�cup�ration des informations par le buffer*/
				while((msg=in.readLine())!=null)
				{
					String id_String =  regexSearch("(?<=id: )\\d+", msg);
					String pseudo = regexSearch("(?<=pseudo: )\\S+", msg);
					String adresse_string = regexSearch("(?<=adresse: )\\S+", msg);
					String servPortTCP_String =  regexSearch("(?<=tcp: )\\d+", msg);
					String statut_String = regexSearch("(?<=statut: )\\d+", msg);
					String etat_String = regexSearch("(?<=etat: )\\d+", msg);
					
					int tcpserv = Integer.parseInt(servPortTCP_String);
					int id = Integer.parseInt(id_String);
					boolean statut=Boolean.parseBoolean(statut_String);
					InetAddress adresse =  InetAddress.getByName(adresse_string);

					/*On ne traite le contact que si c'est un utilisateur externe sinon l'ajout passe par l'udp*/
					if (!statut)
					{

						/*On verifie qu'il n'est pas deja dans la liste*/
						for(Iterator<Contact> it =  agent.getNetworkManager().getconnectedUser().iterator();it.hasNext();)
						{
							Contact c = (Contact)it.next();
							if(c.getId() == id)
							{
								it.remove();
							}
						}	

						/*On verifie que ce n'est pas nous meme*/
						if (id!=agent.getSelf().getId()&&!(etat_String.equals("0")))
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
