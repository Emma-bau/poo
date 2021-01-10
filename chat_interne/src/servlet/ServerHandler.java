package servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerHandler extends Thread{
	
	public ServerHandler() {
		start();
	}
	
	/*A modifier mais fonctionne pour l'instant très bien, à voir comment rajouter les nouveaux utilisateurs*/
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
