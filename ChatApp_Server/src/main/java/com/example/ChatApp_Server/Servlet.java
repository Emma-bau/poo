package com.example.ChatApp_Server;

import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


class User{
	private String pseudo;
	private String ID;
	private String tcp;
	private String adresse;
	private String interne;




	public User (String pseudo, String ID, String interne, String tcp, String adresse ){
		this.pseudo=pseudo;
		this.ID=ID;
		this.interne=interne;
		this.tcp=tcp;
		this.adresse=adresse;

	}

	public String getAdresse() {
		return adresse;
	}

	public String getTcp() {
		return tcp;
	}



	public String getID() {
		return ID;
	}


	public String getPseudo() {
		return pseudo;
	}

	public String getInterne() {
		return interne;
	}

	public String afficher()
	{
		return ("USER : "+pseudo+" "+ID);
	}

}

@WebServlet(name = "Servlet", value = "/servlet")
public class Servlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ArrayList<User> connectedUsers;

	public Servlet() {
		this.connectedUsers = new ArrayList<>();
		connectedUsers.add(new User("pseudo", "ID","interne","etat","statut"));
		connectedUsers.add(new User("courgette", "3600","oui","1","connected"));
	}

	public void init() {
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		response.setContentType("text/plain");
		PrintWriter pw =response.getWriter();
		pw.println("Entrer dans la methode GET");
		pw.println(connectedUsers.size());
		for (int i = 0; i<connectedUsers.size();i++) {
			User user = connectedUsers.get(i);
			pw.println("id: "+user.getID()+":"+"pseudo: "+user.getPseudo()+" :"+"adresse: "+user.getAdresse()+" :"+"tcp: "+user.getTcp()+":"+" statut: "+user.getInterne()+": connected");
		}
		pw.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String ID = request.getHeader("ID");
		String pseudo = request.getHeader("pseudo");
		String adresse = request.getHeader("adresse");
		String tcp = request.getHeader("tcp");
		String interne = request.getHeader("status");
		String etat = request.getHeader("etat");
	
		response.setContentType("text/plain");
		PrintWriter pw =response.getWriter();
		User C = new User(pseudo,ID,interne,tcp,adresse);
		//pw.println(C.afficher());
		connectedUsers.add(C);

		/*if(etat=="1")
		{

		}
		else if (etat=="2")
		{
			for(User U : connectedUsers)
			{
				if(U.getID()==ID)
				{
					connectedUsers.remove(U);
					connectedUsers.add(new User(pseudo,ID,interne,tcp,adresse));

				}
			}
		}
		else
		{
			for(User U : connectedUsers)
			{
				if(U.getID()==ID)
				{
					connectedUsers.remove(U);
				}
			}
		}*/


	}



	public void destroy() {
	}


}