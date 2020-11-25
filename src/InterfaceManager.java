import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class InterfaceManager extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final JLabel label = new JLabel("Connexion");
	final static String LOOKANDFEEL = "Motif";
	private Agent agent;
	
	public InterfaceManager(Agent agent) 
	{
		this.agent = agent;
		initLookAndFeel();
		
		//fenêtre de connexion 
		 //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
        
        //Create and set up the window.
        JFrame frame = new JFrame("Chat Session");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel label = new JLabel("Chat");
        frame.getContentPane().add(label);
        
        Component connexion = createConnexionButton();
        frame.getContentPane().add(connexion, BorderLayout.CENTER);
        
        //Display the window.
        frame.pack();
        //à dynamiser
        frame.setSize(1000,800);
        frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		System.out.println("Connexion réussie");
    }
	
	//Style 
	 private static void initLookAndFeel() {
	        
	        // Swing allows you to specify which look and feel your program uses--Java,
	        // GTK+, Windows, and so on as shown below.
	        String lookAndFeel = null;
	        
	        if (LOOKANDFEEL != null) {
	            if (LOOKANDFEEL.equals("Metal")) {
	                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
	            } else if (LOOKANDFEEL.equals("System")) {
	                lookAndFeel = UIManager.getSystemLookAndFeelClassName();
	            } else if (LOOKANDFEEL.equals("Motif")) {
	                lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
	            } else if (LOOKANDFEEL.equals("GTK+")) { //new in 1.4.2
	                lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
	            } else {
	                System.err.println("Unexpected value of LOOKANDFEEL specified: "
	                        + LOOKANDFEEL);
	                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
	            }
	            
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
	
	
	
	
	
	
	public Component createConnexionButton()
	{
		JButton button = new JButton("Connexion");
        button.setMnemonic(KeyEvent.VK_I);
        button.addActionListener(this);
        label.setLabelFor(button);
        
        JPanel pane = new JPanel(new GridLayout(0, 1));
        pane.add(button);
        pane.add(label);
        pane.setBorder(BorderFactory.createEmptyBorder(
                30, //top
                30, //left
                10, //bottom
                30) //right
                );
        
        return pane;
	}
	
	
	

	
	
	
	
    
    	
}