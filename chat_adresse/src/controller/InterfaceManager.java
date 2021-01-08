package controller;
import javax.swing.*;

import main.Agent;
import view.MainInterface;

import java.awt.*;
import java.awt.event.*;


public class InterfaceManager extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	final static String LOOKANDFEEL = "System";
	private Agent agent;

	private JFrame frame;
	private JButton LOG_IN;
	private JButton pseudoChange;
	private JPanel panel;
	private JLabel labelId, labelPassword;
	private final JTextField  inputId, inputPassword;
	private MainInterface mInterface;

	
	public InterfaceManager(Agent agent) 
	{
		this.agent = agent;
		this.frame = new JFrame();
		
		initLookAndFeel();
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setTitle("Log In Chat Session");
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.mInterface = null;

		labelId = new JLabel();
		labelId.setText("ID:");
		inputId = new JTextField(15);
		labelPassword = new JLabel();
		labelPassword.setText("Password:");
		inputPassword = new JPasswordField(15);

		LOG_IN = new JButton("Log In");

		panel = new JPanel(new GridLayout(3,1));
		frame.add(panel);
		panel.add(labelId);
		panel.add(inputId);
		panel.add(labelPassword);
		panel.add(inputPassword);
		panel.add(LOG_IN);
		LOG_IN.addActionListener(this);
		frame.getContentPane().add(panel);
	    frame.setVisible(true);
	}
	
	
	public MainInterface getMainInterface() {
		return this.mInterface;
	}
	
	
	public JFrame getFrame() {
		return this.frame;
	}
	
	
	public void actionPerformed(ActionEvent ae){
		//bouton log_in
		if (ae.getSource() == LOG_IN) {
			askIdAction();
		}
		//bouton pseudoChange
		else {
			askPseudoAction();
		}
	}
	
	
	public void askIdAction() {
		String strid = inputId.getText();
		try {
			int id = Integer.parseInt(strid);
			agent.getSelf().setId(id);
			String password = inputPassword.getText();

			if (agent.getIdManager().verifyID(id,password) == 1) {
				System.out.println("identifiants valides, connexion...");
				labelId.setText("Chose a pseudo (username): ");
				pseudoChange = new JButton("Enter");
				panel.remove(labelPassword);
				panel.remove(inputPassword);
				panel.remove(LOG_IN);
				inputId.setText("");
				panel.add(pseudoChange);
				pseudoChange.addActionListener(this);
			}

			else if (agent.getIdManager().verifyID(id,password) == 2) {
				JOptionPane.showMessageDialog(this,"Incorrect password",
						"Error",JOptionPane.ERROR_MESSAGE);
			}

			else {
				JOptionPane.showMessageDialog(this,"Incorrect ID",
						"Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		catch (NumberFormatException e){
			JOptionPane.showMessageDialog(this,"ID is not a number",
					"Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	public void askPseudoAction() {
		String pseudo = inputId.getText();
		int result = agent.setPseudo(pseudo);
		if (result == 0) {
			System.out.println("Pseudo:" + agent.getPseudoManager().getPseudo());
			frame.dispose();
			new Thread(this.mInterface = new MainInterface(this.agent,this)).start();
		}
		else if (result == 1){
			JOptionPane.showMessageDialog(this,"Pseudo already in use",
					"Error",JOptionPane.ERROR_MESSAGE);
		}
		else if (result == 2){
			JOptionPane.showMessageDialog(this,"Bad pseudo: forbidden character(s) detected",
					"Error",JOptionPane.ERROR_MESSAGE);
		}
		else if (result == 3){
			JOptionPane.showMessageDialog(this,"Pseudo length must be >2 and <12",
					"Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	private static void initLookAndFeel() { 
		String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (ClassNotFoundException e) {
			System.err.println("Couldn't find class for specified look and feel:"+ lookAndFeel);
		} catch (UnsupportedLookAndFeelException e) {
			System.err.println("Can't use the specified look and feel ("+ lookAndFeel+ ") on this platform.");
		} catch (Exception e) {
			System.err.println("Couldn't get specified look and feel ("+ lookAndFeel+ "), for some reason.");
		}
	}
}
