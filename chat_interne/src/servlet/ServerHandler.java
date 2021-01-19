package servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Agent;
import model.Contact;

public class ServerHandler extends Thread{

	private Agent agent;

	public ServerHandler(Agent agent) {
		this.agent = agent;
		start();
	}

	/*Notification au serveur de notre connexion*/
	public void notifyServer(int etat)
	{
		String url = "https://srv-gei-tomcat.insa-toulouse.fr/Server_Jacques_Baudoint/servlet";
		try
		{
			String msg;
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
			
			connection.setRequestProperty("etat", "1");


			if (etat == 0) /*deconnexion*/
				connection.setRequestProperty("etat", "0");
			else if (etat==1) /*connexion*/
				connection.setRequestProperty("etat", "1");
			else if (etat==2) /*changement de pseudo*/
				connection.setRequestProperty("etat", "2");
			else
				System.out.println("Probleme dans le choix de l'�tat pour le serveur");
			BufferedReader in= new BufferedReader (new InputStreamReader (connection.getInputStream()));
			
			connection.disconnect();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/*Chargement de la liste des nouveaux utilisateurs distants*/
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
				msg = in.readLine();
				System.out.println(msg);
				msg = in.readLine();
				System.out.println(msg);
				msg = in.readLine();
				System.out.println(msg);
				msg = in.readLine();
				System.out.println(msg);
				/*R�cup�ration des informations par le buffer*/
				while((msg=in.readLine())!=null)
				{
					System.out.println("Message serveur " + msg);
					String id_String =  regexSearch("(?<=id: )\\d+", msg);
					String pseudo = regexSearch("(?<=pseudo: )\\S+", msg);
					String adresse_string = regexSearch("(?<=adresse: )\\S+", msg);
					String servPortTCP_String =  regexSearch("(?<=tcp: )\\d+", msg);
					String statut_String = regexSearch("(?<=statut: )\\d+", msg);

					int tcpserv = Integer.parseInt(servPortTCP_String);
					int id = Integer.parseInt(id_String);
					boolean statut=Boolean.parseBoolean(statut_String);
					System.out.println("Pseudo " +pseudo+" ID "+id+" TCP "+tcpserv+" Adresse "+adresse_string+ " Statut "+statut_String);
					InetAddress adresse =  InetAddress.getByName(adresse_string);
					

					/*On ne traite le contact que si c'est un utilisateur externe*/
					/*if (!statut)
					{
						boolean trouve = false;
						System.out.println("arrivez dans le if");
						for(Contact C : agent.getNetworkManager().getconnectedUser())
						{
							if ( C.getId() == id || C.getId()==agent.getSelf().getId())
							{
								trouve = true;
							}
						}
						if (!trouve)
						{
							agent.getNetworkManager().getconnectedUser().add(new Contact(tcpserv,pseudo,id,adresse));
						}
					}*/
				}
				connection.disconnect();

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	public void run() {
		while(true) {
			loadServer();	
			try {
				Thread.sleep(20000);
			}
			catch(InterruptedException e) {}
		}
	}

	public static String regexSearch(String regex, String input) {
		Matcher m = Pattern.compile(regex).matcher(input);
		if (m.find()) return m.group();
		return null;
	}

}
