import java.net.InetAddress;
import java.util.ArrayList;

public class Contact
{
    private int broadPort;
    private int servPortUdp;
    private String pseudo;
    private InetAddress adresse;
    private int tcp_serv_port;
    

    public Contact(InetAddress adresse)
    {
        this.adresse = adresse;
    }

    public Contact(int serv, String pseudo, InetAddress adresse, int tcp)
    {
        this.servPortUdp=serv;
        this.pseudo=pseudo;
        this.adresse=adresse;
        this.tcp_serv_port=tcp;
    }
    public Contact(int serv, String pseudo, InetAddress adresse)
    {
        this.servPortUdp=serv;
        this.pseudo=pseudo;
        this.adresse=adresse;
    }

    public int getBroadPort() {
        return broadPort;
    }

    public void setBroadPort(int broadPort) {
        this.broadPort = broadPort;
    }

    public int getServPort() {
        return servPortUdp;
    }

    public void setServPort(int servPort) {
        this.servPortUdp = servPort;
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
        System.out.println(" Port : " + servPortUdp+ " Login : " + pseudo+ " adresse : " +  adresse + "tcp : "+ tcp_serv_port);
    }

    public int getTcp_serv_port() {
        return tcp_serv_port;
    }

    public void setTcp_serv_port(int tcp_serv_port) {
        this.tcp_serv_port = tcp_serv_port;
    }

    


    
} 
