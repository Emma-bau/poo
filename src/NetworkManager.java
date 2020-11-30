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
		//Créer un nouveau thread pour lui ?//
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
		/*gérer avec data history*/
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
			/*Création de notre serveur locale d'écoute*/
			ServerSocket serv = new ServerSocket(1234);
			/*On se met en écoute tant que la session est ouverte*/
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
			/*adresse IP et num de port à récupérer, à faire le tableau des récupération et envoit au début*/
			Socket clientSocket = new Socket("127.0.0.1",1999);
			while(Connexion==true)
			{
				
				/*Si on a un message à envoyer*/
				if (getSendMessage()!="")
				{
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
					/*Fonction du message envoyé voulu*/
					out.println(getSendMessage());
					setSendMessage("");	
				}
				
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				/*ou si on reçoit un message, le buffer read_line n'est pas vide*/
				if(in.ready())
				{					
					String input = in.readLine();
					/*Network récupère notre message*/
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
