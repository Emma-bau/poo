import java.io.*;
import java.net.*;
import java.util.*;



public class ClientHandler extends Thread {

	private NetworkManager manager;
	private final Scanner sc = new Scanner(System.in);
	private final int id_port;

    public ClientHandler(NetworkManager Manager, int idPort)
    {
		this.manager = Manager;
		this.id_port=idPort;
    }

	public void run()
	{
		try 
		{
			System.out.println("connexion cote client");
			/*adresse IP et num de port a recuperer, a faire le tableau des recuperation et envoit au debut*/
			Socket clientSocket = new Socket("127.0.0.1",id_port);
			/*On enregistre dans un tableau l'adresse*/
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			Thread envoyer = new Thread(new Runnable() {
				String msg;
				 @Override
				 public void run() {
				   while(true){
					 msg = sc.nextLine();
					 out.println(msg);
				   }
				}
			});
			envoyer.start();
	  
		   Thread recevoir = new Thread(new Runnable() {
			   String msg;
			   @Override
			   public void run() {
				  try {
					msg = in.readLine();
					while(msg!=null){
					   msg = in.readLine();
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
	

    
}
