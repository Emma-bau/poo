import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Agent {

	private InterfaceManager interfaceManager;
	private IDManager idManager;
	private DataManager dataManager;
	private PseudoManager pseudoManager;
	private NetworkManager networkManager;

	public Agent() {
		this.idManager = new IDManager();
		this.dataManager = new DataManager(this);
		this.pseudoManager = new PseudoManager(this);	
		//this.networkManager = new NetworkManager(this);
		
		try {
			this.interfaceManager = new InterfaceManager(this);
			interfaceManager.setSize(400,150);
			interfaceManager.setVisible(true);
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public InterfaceManager getInterfaceManager() {
		return interfaceManager;
	}
	public IDManager getIdManager() {
		return idManager;
	}
	public DataManager getDataManager() {
		return dataManager;
	}
	public PseudoManager getPseudoManager() {
		return pseudoManager;
	}
	public NetworkManager getNetworkManager() {
		return networkManager;
	}


	public static void main(String[] args) {
		Agent main = new Agent();

	}
}
