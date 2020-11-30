import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class InterfaceManager extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static String LOOKANDFEEL = "System";
	private Agent agent;

	JButton SUBMIT;
	JPanel panel;
	JLabel labelId, labelPassword;
	final JTextField  inputId, inputPassword;


	public InterfaceManager(Agent agent) 
	{
		this.agent = agent;
		initLookAndFeel();
		JFrame.setDefaultLookAndFeelDecorated(true);

		labelId = new JLabel();
		labelId.setText("Username:");
		inputId = new JTextField(15);
		labelPassword = new JLabel();
		labelPassword.setText("Password:");
		inputPassword = new JPasswordField(15);

		SUBMIT = new JButton("SUBMIT");

		panel = new JPanel(new GridLayout(3,1));
		panel.add(labelId);
		panel.add(inputId);
		panel.add(labelPassword);
		panel.add(inputPassword);
		panel.add(SUBMIT);
		add(panel,BorderLayout.CENTER);
		SUBMIT.addActionListener(this);
		setTitle("Log In Chat Session");
	}

	public void actionPerformed(ActionEvent ae){
		String strid = inputId.getText();
		int id = Integer.parseInt(strid);
		String password = inputPassword.getText();
		if (agent.getIdManager().verifyID(id,password) == 1) {
			/*PseudoInterface page=new PseudoInterface();
			page.setVisible(true);
			JLabel label = new JLabel("Welcome:"+id);
			page.getContentPane().add(label);*/
			System.out.println("identifiants valides, connexion...");

		}
		else if(agent.getIdManager().verifyID(id,password) == 2){
			System.out.println("mauvais mot de passe");
		}
		else{
			System.out.println("enter the valid username and password");
			JOptionPane.showMessageDialog(this,"Incorrect login or password",
					"Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	/*public void actionPerformed(ActionEvent e) {
		agent.getIdManager().verifyID(id,password);
		System.out.println("Connexion réussie");
	}*/

	//Style 
	private static void initLookAndFeel() { 

		// Swing allows you to specify which look and feel your program uses--Java,
		// GTK+, Windows, and so on as shown below.
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
