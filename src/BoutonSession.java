import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoutonSession {
	
	private String text;
	private int nb;
	private JButton bouton;
	
	public BoutonSession(String text, int nb) {
		this.text = text;
		this.nb = nb;
		this.bouton = new JButton(text);
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
