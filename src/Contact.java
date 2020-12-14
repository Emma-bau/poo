import java.net.InetAddress;
import java.util.ArrayList;

public class Contact
{
    private int broadPort;
    private int servPortUdp;
    private String pseudo;
    private InetAddress adresse;
    

    public Contact(InetAddress adresse)
    {
        this.adresse = adresse;
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
        System.out.println(" Port : " + servPortUdp+ " Login : " + pseudo+ " adresse : " +  adresse);
    }

    


    
} 
