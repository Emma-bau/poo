import java.util.ArrayList;

public class PseudoManager {
	/*pseudo de l'hote*/
	private String pseudo; 
	/*Liste des pseudos des utilisateurs en ligne*/
	private ArrayList<String> usersPseudoList;
	public Agent agent;
	
	public PseudoManager(Agent agent) {
		this.agent = agent;
		this.usersPseudoList = new ArrayList<String>();
	}
	
	public String getPseudo() {
		return pseudo;
	}
	
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
		this.usersPseudoList.add(pseudo);
		agent.getNetworkManager().notifyPseudoChange(pseudo); //Change le pseudo
	}
	
	public ArrayList<String> getUsersPseudoList() {
		return usersPseudoList;
	}
	
	
	
}