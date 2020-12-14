import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.time.LocalDate;

public class PrivateChatSession extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private Contact contact;
	private JPanel panel;
	private final JTextField  inputId;
	private JButton send;
	
	public PrivateChatSession(Contact contact) {
		this.contact = contact;
		
		panel = new JPanel(new GridLayout(8,1));
		add(panel,BorderLayout.CENTER);
		setTitle("Chat session with " + this.contact.getPseudo());
		JLabel labelH = new JLabel();
		
		inputId = new JTextField(15);
		send = new JButton("Send");
		
		labelH.setText("Derniers messages: ");
		panel.add(labelH);
		
		printHistory();
		
		
		panel.add(inputId);
		panel.add(send);
		send.addActionListener(this);
		panel.revalidate();
		
		
	}
	
	public void printHistory() {
		// récuperer l'historique de conversation avec le contact
		
		// a supp //
		ArrayList<Message> messagesList = new ArrayList<Message>();
		String txt1 = "salut ça va";
		String txt2 = "REPONDS PTN";
		LocalDate date = LocalDate.now();
		Message m1 = new Message(txt1,date,contact);
		Message m2 = new Message(txt2,date,contact);
		messagesList.add(m1);
		messagesList.add(m2);
		//       //
		
		for (Message message: messagesList) {
			JLabel msg = new JLabel();
			msg.setText(message.getContact().getPseudo() + ": " + message.getMessage() + "    (" + message.getTimestamp() + ")   ");
			panel.add(msg);
		}
	}

	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//ENVOIE LE MESSAGE AU CONTACT
		//Mettre à jour l'historique a chaque envoi
	}
}
