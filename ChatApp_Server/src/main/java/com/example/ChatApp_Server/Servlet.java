package com.example.ChatApp_Server;

import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


class User{
	private String pseudo;
	private String ID;
	private InetAddress IPAddress;
	private boolean interne;

	public boolean isInterne() {
		return interne;
	}

	public void setInterne(boolean interne) {
		this.interne = interne;
	}

	public User (String pseudo, String ID, Boolean interne){
		this.pseudo=pseudo;
		this.ID=ID;
		this.interne=interne;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
}

@WebServlet(name = "Servlet", value = "/servlet")
public class Servlet extends HttpServlet {
	private ArrayList<User> connectedUsers;
	private String essaie;


	public Servlet() {
		this.connectedUsers = new ArrayList<>();
		User u1 = new User("101","emma",true);
		User u2 = new User("102","matthieu",false);
		this.connectedUsers.add(u1);
		this.connectedUsers.add(u2);
	}

	public void init() {
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		response.setContentType("text/plain");
		PrintWriter pw =response.getWriter();
		pw.println("Entrer dans la methode GET");
		for (int i = 0; i<connectedUsers.size();i++) {
			User user = connectedUsers.get(i);
			pw.println(user.getID()+":"+user.getPseudo()+" statut : "+user.isInterne()+": connected");
		}
		pw.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String connected = request.getHeader("cmd");
		String ID = request.getHeader("ID");
		String pseudo = request.getHeader("pseudo");
		String interne = request.getHeader("status");
		if(interne=="true")
		{
			connectedUsers.add(new User(pseudo,ID,true));
		}
		else
		{
			connectedUsers.add(new User(pseudo,ID,false));
		}
		
	}



	public void destroy() {
	}

	private User getUser(String i) {
		int j;
		for (j = 0; j < connectedUsers.size(); j++) {
			essaie=connectedUsers.get(j).getID()+" "+i;
			if (connectedUsers.get(j).getID().equals(i)) {
				essaie="ok";
				return connectedUsers.get(j);
			} else {
				continue;
			}
		}
		return null;
	}

}