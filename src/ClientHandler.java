import java.io.*;
import java.net.*;
import java.time.LocalDate;


public class ClientHandler extends Thread {

	private final NetworkManager manager;
	private final Contact user;
	private Socket clientSocket;

    public ClientHandler(NetworkManager Manager, Contact contact)
    {
		this.manager = Manager;
		this.user = contact;

		System.out.println("connexion cote client");
		try
		{
			clientSocket = new Socket(user.getAdresse(),user.getTcp_serv_port());

		}
		catch(IOException e)
		{
			System.out.println("Erreur dans l'ouverture du socket client");
		}

    }

	public void run()
	{
		String recieveMessage;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			recieveMessage = in.readLine();
			while(recieveMessage!=null)
			{
				recieveMessage = in.readLine();
				Message m = new Message(recieveMessage,LocalDate.now(),user);
				manager.setReceiveMessage(m);
			}
			System.out.println("Serveur deconnecte");
		} 
		catch (IOException e) 
		{
			System.out.println("Connection reset (serveur deconnecte)");
		}
	}
	

	public void envoie (Message message)
	{
		try{
			String msg;
			msg = message.getMessage();
			try
			{
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
				System.out.println("Envoie de : " + msg);
				out.println(msg);
				out.close();
			}
			catch(SocketException e)
			{
				System.out.println("Erreur ouverture buffer en out dans clienthandler");
			}
		}
		catch(IOException e)
		{
			System.out.println("Erreur ouverture buffer en out dans clienthandler");
		}
				
	}

	public void afficher()
	{
		System.out.print ("Contact avec : ");
		user.afficher();;
	}
}
