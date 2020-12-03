import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainInterface extends JFrame implements ActionListener, Runnable {
	private static final long serialVersionUID = 1L;
	final static String LOOKANDFEEL = "System";
	final static float TitleFontSize = 40;
	private Agent agent;
	private JFrame frame;
	private JPanel panel1,panel2;
	private BoutonSession bContact;
	private ArrayList<BoutonSession> listBouton;
    
    public MainInterface(Agent agent) {
		this.agent = agent;
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.frame = new JFrame("Chat Session");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.panel1 = new JPanel();
        this.panel2 = new JPanel();
        
        layout();
        toScreenSize();
        
	}
    
    public void layout() {
        JLabel welcomeText = new JLabel();
		welcomeText.setText("Hello "+ agent.getPseudoManager().getPseudo() + "!");
        welcomeText.setFont(welcomeText.getFont().deriveFont(TitleFontSize));        
        
        JLabel connectedListLb = new JLabel();
        connectedListLb.setText("Chose a connected user to talk to:");
        connectedListLb.setFont(connectedListLb.getFont().deriveFont(TitleFontSize));
        
        JButton jb5 = new JButton("pseudo1");

        // Set up the title for different panels
        panel1.setBorder(BorderFactory.createTitledBorder("Infos"));
        panel2.setBorder(BorderFactory.createTitledBorder("Connected Users List"));
        
        // Set up the BoxLayout
        BoxLayout layout1 = new BoxLayout(panel1, BoxLayout.Y_AXIS);
        BoxLayout layout2 = new BoxLayout(panel2, BoxLayout.Y_AXIS);
        panel1.add(welcomeText);
        panel2.add(connectedListLb);
        panel1.setLayout(layout1);
        panel2.setLayout(layout2);

        
        
        // Add the three panels into the frame
        frame.setLayout(new GridLayout());
        frame.add(panel1);
        frame.add(panel2);
       
        frame.pack();
        frame.setVisible(true);
    }
    
    public void toScreenSize() {
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	int screenHeight = screenSize.height;
    	int screenWidth = screenSize.width;
    	frame.setSize(screenWidth, screenHeight);
    }

	public void actionPerformed(ActionEvent ae){
		System.out.println("ne fais rien");
		int i = 0;
		for(BoutonSession b:listBouton) {
			i++;
			if (ae.getSource() == b.getBouton()) {
				System.out.println("ouai ouai");
			}
		}
	}

	@Override
	public void run() {
		while(true) {
			//liste des boutons qui lancent un contact
			listBouton = new ArrayList<BoutonSession>();
			int i = 0;
			
			//efface les boutons deja presents de l'ecran et vide la liste
			for (BoutonSession b:listBouton) {
				frame.remove(b.getBouton());
			}
			listBouton.clear();
			frame.pack();
			
			//cree un bouton par contact connecte et lajoute a la liste
			for (Contact c: agent.getNetworkManager().getconnectedUser()) {
				i++;
				bContact = new BoutonSession(i + " : " + c.getPseudo(),i);
				listBouton.add(bContact);
				panel2.add(bContact.getBouton());
				bContact.getBouton().addActionListener(this);
				bContact.getBouton().setAlignmentX(Component.RIGHT_ALIGNMENT);
				frame.pack();	
			}
			
			toScreenSize();
			try {
				Thread.sleep(8000);
			}
			catch(InterruptedException e) {}
		}
	}

}
