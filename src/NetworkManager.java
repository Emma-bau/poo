import java.net.ServerSocket;
import java.net.Socket;

public class NetworkManager extends Thread {
	private String SendMessage = "";
	private String ReceiveMessage;
	private Agent agent;

	
	
	public NetworkManager(Agent agent) {
		this.agent = agent;
		//Cr�er un nouveau thread pour lui ?//
		server();
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
			
			
			
			
			
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	

}
