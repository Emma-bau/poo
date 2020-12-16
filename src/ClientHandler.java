import java.io.*;
import java.net.*;
import java.time.LocalDate;


public class ClientHandler extends Thread {

	private final NetworkManager manager;
	private final Contact user;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;

    public ClientHandler(NetworkManager Manager, Contact contact)
    {
		this.manager = Manager;
		this.user = contact;

		System.out.println("connexion cote client");
		try{
			clientSocket = new Socket(user.getAdresse(),user.getTcp_serv_port());
			/*Ouverture des buffers en ecriture et en lecture*/
			out = new PrintWriter(clientSocket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
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
			recieveMessage = in.readLine();
			while(recieveMessage!=null)
			{
				recieveMessage = in.readLine();
				Message m = new Message(recieveMessage,LocalDate.now(),user);
				manager.setReceiveMessage(m);
			}
			System.out.println("Serveur deconnecte");
			out.close();
		} 
		catch (IOException e) 
		{
			System.out.println("Connection reset (serveur deconnecte)");
		}
	}
	

	public void envoie (Message message)
	{
		String msg;
		msg = message.getMessage();
		out.println(msg);
		System.out.println("Envoie de : " + msg);
	}

	public void afficher()
	{
		System.out.println ("Contact avec : "+user);
	}
}
