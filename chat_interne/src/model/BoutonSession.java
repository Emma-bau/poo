package model;
import javax.swing.*;
//import java.awt.Dimension;

public class BoutonSession {
	
	/*----------------------------------------------------- Variable ----------------------------------------*/
	
	private String texte;
	private int nb;
	private JButton bouton;
	private Contact contact;
	
	/*----------------------------------------------------- Constructor ----------------------------------------*/
	
	public BoutonSession(String text, int nb, Contact c) {
		this.texte = text;
		this.nb = nb;
		this.contact = c;
		this.bouton = new JButton(texte);
		//bouton.setPreferredSize(new Dimension(300, 50));
	}
	
	/*----------------------------------------------------- GETTER AND SETTER ----------------------------------------*/
	public String toString() {
		String strself = "Bouton du contact " + contact.getPseudo();
		return strself;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public String getText() {
		return texte;
	}
	public void setText(String text) {
		this.texte = text;
	}
	public int getNb() {
		return nb;
	}
	public void setNb(int nb) {
		this.nb = nb;
	}
	public JButton getBouton() {
		return bouton;
	}
	public void setBouton(JButton bouton) {
		this.bouton = bouton;
	}
}
