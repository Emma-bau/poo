package view;
import javax.swing.*;

import controller.InterfaceManager;
import main.Agent;
import model.BoutonSession;
import model.Contact;
import model.Message;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainInterface extends JFrame implements ActionListener, Runnable {
	private static final long serialVersionUID = 1L;
	final static String LOOKANDFEEL = "System";
	final static float TitleFontSize = 12;

	private JFrame frame;
	private JPanel panel1,panel2;
	private Agent agent;
	private InterfaceManager interfaceM;
	private BoutonSession bContact;
	private ArrayList<BoutonSession> listBouton;
	private ArrayList<PrivateChatSession> chatSessionList;
	private JButton bChangePseudo;
	private JLabel welcomeText;


	public MainInterface(Agent agent, InterfaceManager interfaceM) {
		this.agent = agent;
		this.interfaceM = interfaceM;
		this.chatSessionList = new ArrayList<PrivateChatSession>();

		JFrame.setDefaultLookAndFeelDecorated(true);
		this.frame = new JFrame("Chat Session");
		frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		//gere la deconnexion
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				onExit();
			}
		});

		frame.setResizable(false);

		welcomeText = new JLabel();
		this.bChangePseudo = new JButton("Change Pseudo");
		bChangePseudo.addActionListener(this);
		this.panel1 = new JPanel();
		this.panel2 = new JPanel();

		layout();
		toScreenSize();
	}

	//action a la deconnexion
	public void onExit() {
		for(PrivateChatSession pcs : chatSessionList) {
			pcs.dispose();
		}
		agent.deconnexion();
		System.exit(0);
	}

	public InterfaceManager getInterfaceM() {
		return this.interfaceM;
	}


	public void layout() {
		welcomeText.setText("Hello "+ agent.getPseudoManager().getPseudo() + "!");
		welcomeText.setFont(welcomeText.getFont().deriveFont(TitleFontSize+3));

		JLabel connectedListLb = new JLabel();
		connectedListLb.setText("Chose a connected user to talk to:");
		connectedListLb.setFont(connectedListLb.getFont().deriveFont(TitleFontSize));
		
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
			@SuppressWarnings("unused")
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
				}
			}
		}
	}


	public void updateChatSessionMessages(Message m) {
		for(PrivateChatSession pcs : chatSessionList) {
			if (pcs.getContact() == m.getAuthor()) {
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

	public void updatePseudo() {
		welcomeText.setText("Hello "+ agent.getPseudoManager().getPseudo() + "!");
	}

}
