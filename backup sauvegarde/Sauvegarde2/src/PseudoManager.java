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

	public int setPseudo(String pseudo) {
		for (Contact c: agent.getNetworkManager().getconnectedUser()) {
			if (c.getPseudo() == pseudo) {
				return 1;
			}
		}
		if (pseudo.contains(" ") || pseudo.contains("\n") || pseudo.contains("DROP ")) {
			return 2;
		}
		else if(pseudo.length()>=12 || pseudo.length() <= 2) {
			return 3;
		}
		this.pseudo = pseudo;
		agent.getSelf().setPseudo(pseudo);
		this.usersPseudoList.add(pseudo);
		return 0;
	}

	public ArrayList<String> getUsersPseudoList() {
		return usersPseudoList;
	}

}
