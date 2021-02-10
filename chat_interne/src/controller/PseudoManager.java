package controller;
import java.util.ArrayList;

import main.Agent;
import model.Contact;

public class PseudoManager {
	
	/*-----------------------------------------------------Variable ----------------------------------------*/
	
	/*host pseudo*/
	private String pseudo;
	/*List of pseudo of users'online*/
	private ArrayList<String> usersPseudoList;
	public Agent agent;

	/*-----------------------------------------------------Constructor ----------------------------------------*/
	public PseudoManager(Agent agent) {
		this.agent = agent;
		this.usersPseudoList = new ArrayList<String>();
	}
	
	/*-----------------------------------------------------Getter and Setter ----------------------------------------*/

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
