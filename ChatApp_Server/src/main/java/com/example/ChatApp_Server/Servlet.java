package com.example.ChatApp_Server;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.*;


import javax.servlet.annotation.*;


/*-----------------------------------------------------CLASS CONTACT ----------------------------------------*/

class Contact{

	/*----------------------------------------------------- Variable ----------------------------------------*/
	private String pseudo;
	private String ID;
	private String tcp;
	private String adresse;
	private String interne;
	private String etat;


	/*----------------------------------------------------- Constructor ----------------------------------------*/
	public Contact (String pseudo, String ID, String interne, String tcp, String adresse, String etat ){
		this.pseudo=pseudo;
		this.ID=ID;
		this.interne=interne;
		this.tcp=tcp;
		this.adresse=adresse;
		this.etat=etat;
	}


	/*----------------------------------------------------- Getter and Setter ----------------------------------------*/
	public String getEtat() {
		return etat;
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


/*-----------------------------------------------------CLASS SERVLET ----------------------------------------*/
@WebServlet(name = "Servlet", value = "/servlet")
public class Servlet extends HttpServlet {


	/*----------------------------------------------------- Variable ----------------------------------------*/
	private static final long serialVersionUID = 1L;
	private ArrayList<Contact> connectedUsers;


	/*----------------------------------------------------- Constructor ----------------------------------------*/
	public Servlet() {
		this.connectedUsers = new ArrayList<>();
	}


	/*----------------------------------------------------- Function ----------------------------------------*/
	
	public void init() {
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		response.setContentType("text/plain");
		PrintWriter pw =response.getWriter();
		for (Iterator<Contact> it =  connectedUsers.iterator();it.hasNext();) 
		{
			Contact user = (Contact)it.next();
			pw.println("id: "+user.getID()+":"+"pseudo: "+user.getPseudo()+" :"+"adresse: "+user.getAdresse()+" :"+"tcp: "+user.getTcp()+":"+" statut: "+user.getInterne()+":"+" etat: "+user.getEtat()+": connected");
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
		Contact C = new Contact(pseudo,ID,interne,tcp,adresse,etat);
		
		/*connection*/
		if(etat.equals("1"))
		{
			connectedUsers.add(C);
		}
		/*change pseudo or disconnection*/
		else
		{
			/*Find it in the list*/
			for(Contact U : connectedUsers )
			{
				if(U.getID() == C.getID())
				{
					connectedUsers.remove(U);
				}
			}
			connectedUsers.add(C);}		
		}

	public void destroy() {
	}


}