package view;
import javax.swing.*;

import main.Agent;
import model.Contact;
import model.Message;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class PrivateChatSession extends JFrame implements ActionListener{

	/*----------------------------------------------------- Variable ----------------------------------------*/
	
	private static final long serialVersionUID = 1L;
	private Agent agent;
	private Contact contact;
	
	private JFrame frame;
	private JPanel chatHistoryPanel, sendCheckPanel;
	private ArrayList<Message> messagesList;
	private JTextField inputText;
	private JLabel labelH;

	/*----------------------------------------------------- Constructor ----------------------------------------*/
	
	public PrivateChatSession(Contact contact, Agent agent) {   

		this.contact = contact;
		this.agent = agent;
		this.frame = new JFrame();
		this.messagesList = new ArrayList<Message>();
		this.inputText = new JTextField(15);
		this.chatHistoryPanel = new JPanel();
		this.sendCheckPanel = new JPanel(new GridLayout(2,1));
		
		this.labelH = new JLabel();
		labelH.setText("Last messages: ");
		
		frame.setTitle("Chat session with " + this.contact.getPseudo());
		
		System.out.println("Chargement de l'historique a l'ouverture de la session avec : " + contact.getPseudo());
		
		createSendCheck();
		createChatHistory(agent.getSelf().getId(),contact.getId());
		frame.add(labelH);
		frame.add(chatHistoryPanel);
		frame.add(sendCheckPanel);
		frame.getContentPane().add(BorderLayout.NORTH,labelH);
		frame.getContentPane().add(BorderLayout.CENTER,chatHistoryPanel);
		frame.getContentPane().add(BorderLayout.SOUTH,sendCheckPanel);
		frame.repaint();
	}

	/*----------------------------------------------------- Getters and setters ----------------------------------------*/
	
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
	
	/*-----------------------------------------------------Function ----------------------------------------*/
	
	public void createChatHistory(int auteur, int dest) {
		
		this.messagesList = agent.getDataManager().loadHistory(auteur,dest);
		int size = messagesList.size();
		this.frame.remove(this.chatHistoryPanel);
		this.chatHistoryPanel = new JPanel(new GridLayout(size,2));
		
		for (Message message: messagesList) {
			JLabel msg = new JLabel();
			JLabel date = new JLabel();
			
			//hour:minute to good format
			String minute;
			String heure;
			if (message.getMinute() <= 9) {
				minute = "0" + String.valueOf(message.getMinute());
			}else {
				minute = Integer.toString(message.getMinute());
			}
			if (message.getHour() <= 9) {
				heure = "0" + String.valueOf(message.getHour());
			}else {
				heure = Integer.toString(message.getHour());
			}
			
			//print message and time stamp
			msg.setText(message.getAuthor().getPseudo() + ":  " + message.getMessage());
			date.setText(heure + ":" + minute + "  ("+message.getDate()+")");
			
			chatHistoryPanel.add(msg);
			chatHistoryPanel.add(date);
		}

		frame.add(chatHistoryPanel);
		frame.setSize(400,100+size*12);
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
		System.out.println("Message received: " + m.getMessage() + " from " + m.getAuthor().getPseudo() + "  (ID: " + m.getAuthor().getId() + " )");
		try {
			Thread.sleep(100); // waits for the database update 
		}catch(InterruptedException e) {}
		
		createChatHistory(m.getAuthor().getId(), m.getReceiver().getId());
	}

	@Override
	// send message
	public void actionPerformed(ActionEvent arg0) { 
		LocalDate date = LocalDate.now();
		int hour = LocalDateTime.now().getHour();
		int minute = LocalDateTime.now().getMinute();
		
		if (inputText.getText().length() > 30) {
			System.out.println("message trop long");
			JOptionPane.showMessageDialog(this,"Message is too long",
					"Error",JOptionPane.ERROR_MESSAGE);
		}
		else {
			Message newMessage = new Message(inputText.getText(),date,hour,minute,agent.getSelf(),contact);
			agent.sendMessageTo(newMessage);
			agent.getDataManager().add(newMessage);
			createChatHistory(newMessage.getAuthor().getId(),newMessage.getReceiver().getId());
		}
		inputText.setText("");
	}
}
