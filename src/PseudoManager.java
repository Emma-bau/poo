import java.util.ArrayList;

public class PseudoManager {
	/*pseudo de l'hote*/
	private String pseudo; 
	/*Liste des pseudos des utilisateurs en ligne*/
	private ArrayList<String> usersPseudoList = new ArrayList<String>();
	
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public void notifyNewPseudo()
	{
	}
	
	
	
}