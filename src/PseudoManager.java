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

	public boolean setPseudo(String pseudo) { //doit à terme, vérifier si le pseudo est valide
		this.pseudo = pseudo;
		this.usersPseudoList.add(pseudo);
		//agent.getNetworkManager().notifyPseudoChange(pseudo); //envoie le nouveau pseudo sur le reseau
		return true;
	}

	public ArrayList<String> getUsersPseudoList() {
		return usersPseudoList;
	}



}