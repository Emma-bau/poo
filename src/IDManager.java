import java.util.ArrayList;

public class IDManager {
	
	//private ArrayList<Integer> idList;
	//private String password;
	private ArrayList<User> usersTable;
	
	public IDManager() {
		this.usersTable = new ArrayList<User>();
		createUsersTable();
	}
	
	//Retourne un int en fonction de si l'id et le password donnés correspondent
	public int verifyID(int id, String password) {
		for (User u:usersTable) {
			if (id == u.getId() ) {
				if (password == u.getPassword()) {
					return 1; //1 = valid ID
				} else {
					return 2; //2 = bad password
				}
			}
		}
		return 3; // 3 = wrong ID
	}
	
	//Liste provisoire des comptes valides
	private void createUsersTable() {
		User user1 = new User(100,"toto");
		User user2 = new User(101,"toto");
		User admin = new User(999,"admin");
		usersTable.add(user1);
		usersTable.add(user2);
		usersTable.add(admin);
	}
}
