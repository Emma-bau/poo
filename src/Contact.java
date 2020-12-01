import java.net.InetAddress;

public class Contact
{
    private int broadPort;
    private int servPort;
    private String pseudo;
    private InetAddress adresse;

    public Contact(int broad, int serv, String pseudo, InetAddress adresse)
    {
        this.broadPort = broad;
        this.servPort=serv;
        this.pseudo=pseudo;
        this.adresse=adresse;
    }


    
} 
