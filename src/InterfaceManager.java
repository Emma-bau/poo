import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class InterfaceManager extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	final static String LOOKANDFEEL = "System";
	private Agent agent;

	private JButton LOG_IN;
	private JButton pseudoChange;
	private JPanel panel;
	private JLabel labelId, labelPassword;
	private final JTextField  inputId, inputPassword;
	private MainInterface mInterface;

	public InterfaceManager(Agent agent) 
	{
		this.agent = agent;
		initLookAndFeel();
		JFrame.setDefaultLookAndFeelDecorated(true);

		this.mInterface = null;

		labelId = new JLabel();
		labelId.setText("ID:");
		inputId = new JTextField(15);
		labelPassword = new JLabel();
		labelPassword.setText("Password:");
		inputPassword = new JPasswordField(15);

		LOG_IN = new JButton("Log In");

		panel = new JPanel(new GridLayout(3,1));
		panel.add(labelId);
		panel.add(inputId);
		panel.add(labelPassword);
		panel.add(inputPassword);
		panel.add(LOG_IN);
		add(panel,BorderLayout.CENTER);
		LOG_IN.addActionListener(this);
		setTitle("Log In Chat Session");
	}

	public JFrame getMainInterface() {
		return this.mInterface;
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
		boolean result = agent.setPseudo(pseudo);
		if (result) {
			System.out.println("Pseudo:" + agent.getPseudoManager().getPseudo());
			new Thread(new MainInterface(this.agent,this)).start();
		}
		else {
			System.out.println("bad pseudo");
			JOptionPane.showMessageDialog(this,"Bad pseudo, try again",
					"Error",JOptionPane.ERROR_MESSAGE);
		}
	}


	//look and feel
	private static void initLookAndFeel() { 
		String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (ClassNotFoundException e) {
			System.err.println("Couldn't find class for specified look and feel:"
					+ lookAndFeel);
			System.err.println("Did you include the L&F library in the class path?");
			System.err.println("Using the default look and feel.");
		} catch (UnsupportedLookAndFeelException e) {
			System.err.println("Can't use the specified look and feel ("
					+ lookAndFeel
					+ ") on this platform.");
			System.err.println("Using the default look and feel.");
		} catch (Exception e) {
			System.err.println("Couldn't get specified look and feel ("
					+ lookAndFeel
					+ "), for some reason.");
			System.err.println("Using the default look and feel.");
			e.printStackTrace();
		}
	}
}
