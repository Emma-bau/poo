package model;
import java.net.InetAddress;

public class Contact
{
    
    private String pseudo;
    private InetAddress adresse;
    private int id;
    
    private int udp_serv_port;
    private int tcp_serv_port;
    
    /*Identification pour le client handle*/
    private int numClient;
    private boolean isClient; 
    
    /*Pour les contacts externes à l'entreprise qui n'ont pas besoin d'un port udp*/
    public Contact( int tcp_port, String pseudo,int id,InetAddress adresse)
    {
        this.tcp_serv_port=tcp_port;
        this.pseudo=pseudo;
        this.id=id;
        this.adresse=adresse;
    }
    
    /*Pour les contacts internes à l'entreprise*/
	public Contact(int udp_port, int tcp_port, String pseudo, InetAddress adresse, int id)
    {
        this.udp_serv_port=udp_port;
        this.tcp_serv_port=tcp_port;
        this.pseudo=pseudo;
        this.adresse=adresse;
        this.id=id;
    }
	
	public int getUdp_serv_port() {
		return udp_serv_port;
	}

	public void setUdp_serv_port(int udp_serv_port) {
		this.udp_serv_port = udp_serv_port;
	}

	public int getId() {
			return id;
		}

	public void setId(int id) {
			this.id = id;
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

   /* public void afficher ()
    {
        System.out.println("CONTACT : Port UDP: " + udp_serv_port + " / Port TCP: " +  tcp_serv_port + " / Login : " + pseudo + " / adresse : " +  adresse+ " /ID :"+id);
    }*/
    
    public void afficher ()
    {
        System.out.println("CONTACT : Port TCP: " +  tcp_serv_port + " / Login : " + pseudo +" /ID :"+id);
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
