import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class MainInterface extends JFrame implements ActionListener, Runnable {
	private static final long serialVersionUID = 1L;
	final static String LOOKANDFEEL = "System";
	final static float TitleFontSize = 12;
	
	private JFrame frame;
	private JPanel panel1,panel2;
	private Agent agent;
	private InterfaceManager interfaceM;
	private BoutonSession bContact, bChangePseudo;
	private ArrayList<BoutonSession> listBouton;
	private ArrayList<PrivateChatSession> chatSessionList;
    
	
    public MainInterface(Agent agent, InterfaceManager interfaceM) {
		this.agent = agent;
		this.interfaceM = interfaceM;
		this.chatSessionList = new ArrayList<PrivateChatSession>();
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.frame = new JFrame("Chat Session");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        
        this.panel1 = new JPanel();
        this.panel2 = new JPanel();
        
        layout();
        toScreenSize();
	}
    
    
    public InterfaceManager getInterfaceM() {
    	return this.interfaceM;
    }
    
    
    public void layout() {
        JLabel welcomeText = new JLabel();
		welcomeText.setText("Hello "+ agent.getPseudoManager().getPseudo() + "!");
        welcomeText.setFont(welcomeText.getFont().deriveFont(TitleFontSize+3));
        
        JLabel connectedListLb = new JLabel();
        connectedListLb.setText("Chose a connected user to talk to:");
        connectedListLb.setFont(connectedListLb.getFont().deriveFont(TitleFontSize));
        
        JButton bChangePseudo = new JButton("Change Pseudo");
        panel1.setBorder(BorderFactory.createTitledBorder("Infos"));
        panel2.setBorder(BorderFactory.createTitledBorder("Connected Users List"));
        
        BoxLayout layout1 = new BoxLayout(panel1, BoxLayout.Y_AXIS);
        BoxLayout layout2 = new BoxLayout(panel2, BoxLayout.Y_AXIS);
        panel1.add(welcomeText);
        panel2.add(connectedListLb);
        panel1.setLayout(layout1);
        panel2.setLayout(layout2);

        frame.setLayout(new GridLayout());
        frame.add(panel1);
        frame.add(panel2);
        panel1.add(bChangePseudo);
        
        frame.setVisible(true);
    }
    
    
    public void toScreenSize() {
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	int screenHeight = screenSize.height-(screenSize.height/2);
    	int screenWidth = screenSize.width-(screenSize.width/2);
    	frame.setSize(screenWidth, screenHeight);
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
    	int dx = centerPoint.x - screenWidth / 2;
        int dy = centerPoint.y - screenHeight / 2;    
        frame.setLocation(dx, dy);
    }
    
    
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource() == bChangePseudo) {
			ChangePseudoInterface cpi = new ChangePseudoInterface(agent);
		}
		//Fonctionnement boutons chavardage
		else {
			for(BoutonSession b:listBouton) {
				if (ae.getSource() == b.getBouton()) {
					agent.establishConnexion(b.getContact());
					PrivateChatSession pcs = new PrivateChatSession(b.getContact(),agent);
					chatSessionList.add(pcs);
					pcs.getFrame().setVisible(true);
					pcs.getFrame().setSize(400,200);
				}
			}
		}
	}
	
	
	public void updateChatSessionMessages(Message m) {
		System.out.println("update main interface, message: " + m);
		for(PrivateChatSession pcs : chatSessionList) {
			if (pcs.getContact() == m.getAuthor()) {
				System.out.println("pcs trouve");
				pcs.updateHistory(m);
			}
		}
	}
	
	
	@Override
	public void run() {
		//liste des boutons qui lancent un contact
		listBouton = new ArrayList<BoutonSession>();
		while(true) {
			//efface les boutons deja presents de l'ecran et vide la liste
			for (BoutonSession b : listBouton) {
				b.getBouton().setVisible(false);
				frame.remove(b.getBouton());
			}
			
			listBouton.clear();
			
			int i = 0;
			//cree un bouton par contact connecte et l'ajoute a la liste
			for (Contact c: agent.getNetworkManager().getconnectedUser()) {
				i++;
				bContact = new BoutonSession(c.getPseudo(),i,c);
				listBouton.add(bContact);
				bContact.getBouton().setSize(100,30);
				panel2.add(bContact.getBouton());
				bContact.getBouton().addActionListener(this);
				bContact.getBouton().setAlignmentX(Component.RIGHT_ALIGNMENT);			
			}
			panel2.repaint();
			frame.revalidate();
			frame.repaint();
			try {
				Thread.sleep(5000);
			}
			catch(InterruptedException e) {}
		}
	}
	
	

}
