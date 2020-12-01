import java.io.*;
import java.net.*;
import java.util.*;

public class TestServer{
    public static void main(String[] args) {

        try {
            ServerSocket ss = new ServerSocket(1234);

            while(true) {
                System.out.println("Awaiting connection");
                Socket link = ss.accept();
                new Thread(new Runner(link)).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}


class Runner implements Runnable {

    final Socket link;

    public Runner(Socket link) {
        this.link = link;
    }

    @Override
    public void run() {
        System.out.println("Thread started");
        try {
            BufferedReader in = new BufferedReader( new InputStreamReader(link.getInputStream()));       
            String input = in.readLine();
            System.out.println(input);
            link.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 




