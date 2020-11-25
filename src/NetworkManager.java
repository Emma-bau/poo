import java.net.ServerSocket;
import java.net.Socket;

public class NetworkManager extends Thread {
	private String SendMessage = "";
	private String RecieveMessage="";
	
	public NetworkManager() {};

	public String getSendMessage() {
		return SendMessage;
	}

	public void setSendMessage(String sendMessage) {
		SendMessage = sendMessage;
	}

	public String getRecieveMessage() {
		return RecieveMessage;
	}

	public void setRecieveMessage(String recieveMessage) {
		RecieveMessage = recieveMessage;
	}

	public void server() 
	{
		try {
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

}
