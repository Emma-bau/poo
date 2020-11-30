import java.io.*;
import java.net.*;


public class NetworkManager extends Thread {
	private String SendMessage = "";
	private String ReceiveMessage;
	private Agent agent;
	private boolean Connexion = false;


	public boolean isConnexion() {
		return Connexion;
	}

	public void setConnexion(boolean connexion) {
		Connexion = connexion;
	}
	
	public NetworkManager(Agent agent) {
		this.agent = agent;
		//Cr�er un nouveau thread pour lui ?//
		server();
		client();
	}


	public String getSendMessage() {
		return SendMessage;
	}

	public void setSendMessage(String sendMessage) {
		SendMessage = sendMessage;
	}

	public String getReceiveMessage() {
		/*g�rer avec data history*/
		return ReceiveMessage;
	}

	public void setReceiveMessage(String recieveMessage) {
		ReceiveMessage = recieveMessage;
	}
	
	public void notifyPseudoChange(String pseudo)
	{
		
	}

	public void server() 
	{
		try 
		{
			/*Cr�ation de notre serveur locale d'�coute*/
			ServerSocket serv = new ServerSocket(1234);
			/*On se met en �coute tant que la session est ouverte*/
			while(true)
			{
				/*Attente de connexion*/
				System.out.println("Awaiting connection");
				Socket SocketTCP = serv.accept();
				new Thread(new NetworkWaiter(SocketTCP,this)).start();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void client()
	{
		try 
		{
			System.out.println("connexion au serveur");
			/*adresse IP et num de port � r�cup�rer, � faire le tableau des r�cup�ration et envoit au d�but*/
			Socket clientSocket = new Socket("127.0.0.1",1999);
			while(Connexion==true)
			{
				
				/*Si on a un message � envoyer*/
				if (getSendMessage()!="")
				{
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
					/*Fonction du message envoy� voulu*/
					out.println(getSendMessage());
					setSendMessage("");	
				}
				
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				/*ou si on re�oit un message, le buffer read_line n'est pas vide*/
				if(in.ready())
				{					
					String input = in.readLine();
					/*Network r�cup�re notre message*/
					System.out.println("Received : "+input);
					setReceiveMessage(input);	
				}
				
			}
			
			
			
			
			
			
			
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	

}
