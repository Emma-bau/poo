import java.io.*;
import java.net.*;

public class ClientTest1 {

    public static void main(String[] args) {

        for(int i = 1 ; i < 3 ; i++) {

            final int link_id = i;
            new Thread(() -> {
                try {
                    Socket s = new Socket("127.0.0.1", 2000);
                    
					PrintWriter out = new PrintWriter(s.getOutputStream(),true);
					/*Fonction du message envoye voulu*/
					out.println("client 1 connecté");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();


        }


    }


}
 
