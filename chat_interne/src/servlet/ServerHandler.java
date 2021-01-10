package servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import main.Agent;

public class ServerHandler extends Thread{
	
	private Agent agent;
	
	public ServerHandler(Agent agent) {
		this.agent = agent;
		start();
	}
	
	public void notifyServer()
	{
		String url = "https://srv-gei-tomcat.insa-toulouse.fr/Server_Jacques_Baudoint/servlet";
		try
		{
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("cmd", "connected");
			connection.setRequestProperty("ID", );
			connection.setRequestProperty("pseudo","courgette");
			connection.setRequestProperty("status","true");
			System.out.println(connection.getResponseCode());
			System.out.println("En vrai, arriver ici");
			connection.disconnect();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*A modifier mais fonctionne pour l'instant tr�s bien, � voir comment rajouter les nouveaux utilisateurs*/
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
				while((msg=in.readLine())!=null)
				{
					
				}
			}
			connection.disconnect();
			
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
				Thread.sleep(300);
			}
			catch(InterruptedException e) {}
		}
	}

}