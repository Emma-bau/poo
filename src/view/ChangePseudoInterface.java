package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.Agent;

public class ChangePseudoInterface extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private final JTextField  inputText;
	private JButton send;
	private Agent agent;
	
	public ChangePseudoInterface(Agent agent) {
		this.agent = agent;
		panel = new JPanel();
		inputText = new JTextField(15);
	}
	
	public void actionPerformed(ActionEvent arg0) {
	}
}
