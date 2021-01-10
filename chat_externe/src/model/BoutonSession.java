package model;
import javax.swing.*;

public class BoutonSession {
	
	private String texte;
	private int nb;
	private JButton bouton;
	private Contact contact;
	
	public BoutonSession(String text, int nb, Contact c) {
		this.texte = text;
		this.nb = nb;
		this.contact = c;
		this.bouton = new JButton(texte);
		bouton.setSize(300,100);
	}

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
