import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.time.LocalDate;


public class PrivateChatSession extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private Agent agent;
	private Contact contact;
	
	private JFrame frame;
	private JPanel chatHistoryPanel, sendCheckPanel;
	private ArrayList<Message> messagesList;
	private JTextField inputText;

	public PrivateChatSession(Contact contact, Agent agent) {   

		this.contact = contact;
		this.agent = agent;
		this.frame = new JFrame();
		this.messagesList = new ArrayList<Message>();
		this.inputText = new JTextField(15);
		this.chatHistoryPanel = new JPanel(new GridLayout(5,1));
		this.sendCheckPanel = new JPanel(new GridLayout(2,1));
		
		JLabel labelH = new JLabel();
		labelH.setText("Last messages: ");
		frame.add(labelH);
		
		frame.add(chatHistoryPanel);
		frame.add(sendCheckPanel);
		frame.getContentPane().add(BorderLayout.NORTH,labelH);
		frame.getContentPane().add(BorderLayout.CENTER,chatHistoryPanel);
		frame.getContentPane().add(BorderLayout.SOUTH,sendCheckPanel);
		
		frame.setTitle("Chat session with " + this.contact.getPseudo());
		
		//messagesList = SAUVEGARDE DE LA BDD a partir d'ici /!\
		
		createChatHistory();
		createSendCheck();
	}

	public String toString() {
		return ("Chat session avec "+ this.contact.getPseudo());
	}
	public Contact getContact() {
		return this.contact;
	}
	public JFrame getFrame() {
		return this.frame;
	}
	
	public ArrayList<Message> getMessagesList() {
		return this.messagesList;
	}
	
	public void createChatHistory() {
		
		// LA MESSAGESLIST DOIT ETRE EGALE A LA BDD A CHAQUE APPEL DE CETTE FONCTION /!\ donc mise a jour ici
		// this.messagesList = (bdd);
		
		int size = messagesList.size();
		this.frame.remove(this.chatHistoryPanel);
		this.chatHistoryPanel = new JPanel(new GridLayout(size,2));

		for (Message message: messagesList) {
			JLabel msg = new JLabel();
			JLabel date = new JLabel();
			msg.setText(message.getAuthor().getPseudo() + ":  " + message.getMessage());
			date.setText("("+message.getTimestamp()+")");
			chatHistoryPanel.add(msg);
			chatHistoryPanel.add(date);
		}
		
		frame.add(chatHistoryPanel);
		frame.setSize(400,100+size*10);
		frame.revalidate();
		frame.repaint();
	}
	
	
	public void createSendCheck() {
		JButton send = new JButton("Send");
		send.addActionListener(this);
		sendCheckPanel.add(inputText);
		sendCheckPanel.add(send);
	}

	// receive message
	public void updateHistory(Message m) {
		System.out.println("updateHistory de private chat session");
		
		// /!\ DOIT MODIFIER LA BDD ICI, PAS LA MESSAGE LIST DIRECTEMENT
		agent.getDataManager().add(m);
		System.out.println("updateBDD de private chat session");
		
		messagesList.add(m); //a supp une fois la bdd fonctionnelle 
		createChatHistory();
	}

	@Override
	// send message
	public void actionPerformed(ActionEvent arg0) { 
		LocalDate date = LocalDate.now();
		if (inputText.getText().length() > 30) {
			
		}
		Message newMessage = new Message(inputText.getText(),date,agent.getSelf(),contact);
		agent.sendMessageTo(newMessage);
		
		// /!\ DOIT MODIFIER LA BDD EGALEMENT
		// (bdd).add(newMessage);
		
		messagesList.add(newMessage); //a supp une fois la bdd fonctionnelle
		createChatHistory();
	}
}
