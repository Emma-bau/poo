import java.util.ArrayList;

public class Agent {

	private InterfaceManager interfaceManager;
	private IDManager idManager;
	private DataManager dataManager;
	private PseudoManager pseudoManager;
	private NetworkManager networkManager;
	
	public Agent() {
		this.interfaceManager = new InterfaceManager(this);
		this.idManager = new IDManager();
		this.dataManager = new DataManager(this);
		this.pseudoManager = new PseudoManager(this);	
		//this.networkManager = new NetworkManager(this);
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
		
	}
}
