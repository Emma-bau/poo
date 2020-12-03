import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PrivateChatSession extends JFrame implements ActionListener{

	private Contact contact;
	private JPanel panel;
	
	public PrivateChatSession(Contact contact) {
		this.contact = contact;
		
		panel = new JPanel();
		setTitle("Chat session with " + contact.getPseudo());
		JLabel labeltest = new JLabel();
		panel.add(labeltest);
	}

	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {

	}
}
