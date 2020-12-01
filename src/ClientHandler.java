import java.io.*;
import java.net.*;
import java.util.*;


public class ClientHandler extends Thread {

    private NetworkManager manager;

    public ClientHandler(NetworkManager Manager)
    {
        this.manager = Manager;
    }






	public void run()
	{
		try 
		{
			System.out.println("connexion cote client");
			/*adresse IP et num de port a recuperer, a faire le tableau des recuperation et envoit au debut*/
			Socket clientSocket = new Socket("127.0.0.1",1234);
			while(manager.isConnexion()==true)
			{
				
				/*Si on a un message a envoyer*/
				/*if (getSendMessage()!="")
				{*/
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
					/*Fonction du message envoye voulu*/
					out.println("courgette");
					manager.setSendMessage("");
				/*}*/
				
				/*BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				/*ou si on recoit un message, le buffer read_line n'est pas vide*/
				//String input = in.readLine();
				/*Network recupere notre message*/
				//System.out.println("Received : "+input);
				//setReceiveMessage(input);	
				
			}
			clientSocket.close();	
			
		}
		catch (Exception e)
		{
			System.out.println("Erreur dans le lancement du client");
			e.printStackTrace();
		}
	}
	

    
}
