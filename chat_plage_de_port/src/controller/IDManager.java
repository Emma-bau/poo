package controller;
import java.util.ArrayList;

import model.User;

public class IDManager {
	
	//private ArrayList<Integer> idList;
	//private String password;
	private ArrayList<User> usersTable;
	
	public IDManager() {
		this.usersTable = new ArrayList<User>();
		createUsersTable();
	}
	
	//Retourne un int en fonction de si l'id et le password donnes correspondent
	public int verifyID(int id, String password) {
		for (User u:usersTable) {
			if (id == u.getId() ) {
				if (password.equals(u.getPassword())) {
					return 1; //1 = valid ID and password
				} else {
					return 2; //2 = bad password
				}
			}
		}
		return 3; // 3 = wrong ID
	}
	
	//Liste des comptes valides
	private void createUsersTable() {
		User user1 = new User(1,"toto");
		User user2 = new User(2,"toto");
		User user3 = new User(3,"toto");
		User user4 = new User(4,"toto");
		User admin = new User(999,"admin");
		usersTable.add(user1);
		usersTable.add(user2);
		usersTable.add(user3);
		usersTable.add(user4);
		usersTable.add(admin);
	}
}
