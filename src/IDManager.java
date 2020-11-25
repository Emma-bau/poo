import java.util.ArrayList;

public class IDManager {
	
	//private ArrayList<Integer> idList;
	//private String password;
	private ArrayList<User> usersTable;
	
	public IDManager() {
		this.usersTable = new ArrayList<User>();
		createUsersTable();
	}
	

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
	
	
	private void createUsersTable() {
		User user1 = new User(100,"toto");
		User user2 = new User(101,"toto");
		User admin = new User(999,"admin");
		usersTable.add(user1);
		usersTable.add(user2);
		usersTable.add(admin);
	}
}
