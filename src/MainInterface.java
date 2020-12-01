import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainInterface extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	final static String LOOKANDFEEL = "System";
	private Agent agent;
    
    public MainInterface(Agent agent) {
		this.agent = agent;
		JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Chat Session");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        toScreenSize(frame);
        layout(frame);
        toScreenSize(frame);
        
	}
    
    public void layout(JFrame frame) {
        JLabel welcomeText = new JLabel();
		welcomeText.setText("Hello "+ agent.getPseudoManager().getPseudo() + "!");
        float fontSize = 40;
        welcomeText.setFont(welcomeText.getFont().deriveFont(fontSize));
		      
        JButton jb5 = new JButton("Button 5 -");        
        JButton jb6 = new JButton("Button 6 --------");
         
        // Define the panel to hold the buttons 
        JPanel panel1 = new JPanel();
        JPanel panel3 = new JPanel();
         
        // Set up the title for different panels
        panel1.setBorder(BorderFactory.createTitledBorder("Infos"));
        panel3.setBorder(BorderFactory.createTitledBorder("RIGHT"));
         
        
        // Set up the BoxLayout
        BoxLayout layout1 = new BoxLayout(panel1, BoxLayout.Y_AXIS);
        BoxLayout layout3 = new BoxLayout(panel3, BoxLayout.Y_AXIS);
        panel1.add(welcomeText);
        panel1.setLayout(layout1);
        panel3.setLayout(layout3);

        

         
        jb5.setAlignmentX(Component.RIGHT_ALIGNMENT);
        jb6.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel3.add(jb5);
        panel3.add(jb6);
         
        // Add the three panels into the frame
        frame.setLayout(new GridLayout());
        frame.add(panel1);
        frame.add(panel3);
       
        frame.pack();
        frame.setVisible(true);


    	
    }
    public void toScreenSize(JFrame frame) {
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	int screenHeight = screenSize.height;
    	int screenWidth = screenSize.width;
    	frame.setSize(screenWidth, screenHeight);
    }

	public void actionPerformed(ActionEvent ae){
		int x;
	}

}
