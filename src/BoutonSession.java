import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoutonSession {
	
	private String text;
	private int nb;
	private JButton bouton;
	private Contact contact;
	
	public BoutonSession(String text, int nb, Contact c) {
		this.text = text;
		this.nb = nb;
		this.contact = c;
		this.bouton = new JButton(text);
	}

	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
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
