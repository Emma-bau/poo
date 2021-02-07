package model;

public class User {

	private int id;
	private String password;
	
	public User(int id, String password) {
		this.id = id;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}
	
}


