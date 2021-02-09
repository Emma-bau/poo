package model;

public class User {
	
	/*----------------------------------------------------- VARIABLE ----------------------------------------*/

	private int id;
	private String password;
	
	/*----------------------------------------------------- CONSTRUCTOR ----------------------------------------*/
	
	public User(int id, String password) {
		this.id = id;
		this.password = password;
	}

	/*----------------------------------------------------- GETTERS ----------------------------------------*/
	public int getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}
	
}


