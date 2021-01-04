package servlet;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;

public class ChatServer extends HttpServlet{
	
	// Liste des utilisateurs connectes sur le serveur
	private ArrayList<User> connectedUsers;
	public static final int USER_CONNECTION_ACTION = 1;
	public static final int USER_DISCONNECTION_ACTION = 2;
	
	ChatServer()
	{
		super();
		connectedUsers = new ArrayList<User>();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		response.setContentType("text/plain");
		
		/*envoie de la réponse a notre client*/
		PrintWriter out = response.getWriter();
		
	}

}
