import java.net.InetAddress;

public class Contact
{
    private int broadPort;
    private int servPort;
    private String pseudo;
    private InetAddress adresse;

    public Contact(int serv, String pseudo, InetAddress adresse)
    {
        this.servPort=serv;
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
        return servPort;
    }

    public void setServPort(int servPort) {
        this.servPort = servPort;
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
        System.out.println(" Port : " + servPort+ " Login : " + pseudo+ " adresse : " +  adresse);
    }

    


    
} 
