import java.io.*;
import java.net.*;



public class ClientHandler extends Thread {

	private final NetworkManager manager;
	private final Contact user;

    public ClientHandler(NetworkManager Manager, Contact contact)
    {
		this.manager = Manager;
		this.user = contact;

    }

	public void run()
	{
		try 
		{
			System.out.println("connexion cote client");
			Socket clientSocket = new Socket(user.getAdresse(),user.getTcp_serv_port());

			/*Ouverture des buffers en écriture et en lecture*/
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			Thread envoyer = new Thread(new Runnable() {
				String msg;

				public void run() {
				   while(manager.isConnexion()){
					
						//On envoie le message si l'adresse correspond à l'adresse de notre client
						if ((manager.getSendMessage() != null ) && (manager.getSendMessage().getContact().getAdresse()==adresse))
						{
							msg = manager.getSendMessage().getMessage();
							out.println(msg);
						}
				   }
				   try
				   {
					clientSocket.close();
				   }
				   catch(IOException e)
				   {
					   System.out.println("Problème fermeture socket tcp client");
				   }
				   
				}
			});
			envoyer.start();
	  
		   Thread recevoir = new Thread(new Runnable() {
			   String msg;
			   Contact client;
			   @Override
			   public void run() {
				  try {
					msg = in.readLine();
					while(msg!=null){
						msg = in.readLine();

						for (Contact C : manager.getconnectedUser())
						{
							if (C.getAdresse() == adresse)
							{
								client = C;
							}
						}
						Message m = new Message(msg,java.time.LocalDate.now(),client);
						manager.setReceiveMessage(m);
					}
					System.out.println("Serveur déconecté");
					out.close();
				  } catch (IOException e) {
					  e.printStackTrace();
				  }
			   }
		   	});
		   	recevoir.start();
			
		}
		catch (Exception e)
		{
			System.out.println("Erreur dans le lancement du client");
			e.printStackTrace();
		}
	}
	

    public void envoie (Message )
}
