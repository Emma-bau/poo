package view;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.Agent;
import javax.swing.*;

public class ChangePseudoInterface extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JPanel panel;
	private final JTextField inputText;
	private JButton send;
	private Agent agent;
	private JLabel text;
	
	public ChangePseudoInterface(Agent agent) {
		this.agent = agent;
		
		this.frame = new JFrame();
		frame.setTitle("Change Pseudo");
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		panel = new JPanel(new GridLayout(3,1));
		text = new JLabel("Enter your new pseudo: ");
		inputText = new JTextField(15);
		send = new JButton("Enter");
		send.addActionListener(this);
		
		frame.add(panel);
		frame.getContentPane().add(panel);
		panel.add(text);
		panel.add(inputText);
		panel.add(send);
		
		frame.setVisible(true);
		frame.setSize(400,200);
		frame.setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String strpseudo = inputText.getText();
		agent.setPseudo(strpseudo);
	}
}
