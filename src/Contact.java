import java.net.InetAddress;

public class Contact
{
    
    private String pseudo;
    private InetAddress adresse;
    private int udp_serv_port;
    private int tcp_serv_port;
    private int numClient;
    private boolean isClient; 

    public Contact(int udp_port, int tcp_port, String pseudo, InetAddress adresse)
    {
        this.udp_serv_port=udp_port;
        this.tcp_serv_port=tcp_port;
        this.pseudo=pseudo;
        this.adresse=adresse;
    }

    public int getServPort() {
        return udp_serv_port;
    }

    public void setServPort(int servPort) {
        this.udp_serv_port = servPort;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public InetAddress getAdresse() {
        return adresse;
    }

    public void setAdresse(InetAddress adresse) {
        this.adresse = adresse;
    }

    public void afficher ()
    {
        System.out.println(" Port : " + udp_serv_port + " Login : " + pseudo+ " adresse : " +  adresse + "tcp : "+ tcp_serv_port);
    }

    public int getTcp_serv_port() {
        return tcp_serv_port;
    }

    public void setTcp_serv_port(int tcp_serv_port) {
        this.tcp_serv_port = tcp_serv_port;
    }

    public int getNumClient() {
        return numClient;
    }

    public void setNumClient(int numClient) {
        this.numClient = numClient;
    }

    public boolean isClient() {
        return isClient;
    }

    public void setClient(boolean isClient) {
        this.isClient = isClient;
    }

    


    
} 
