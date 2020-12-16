import java.util.ArrayList;
import java.time.LocalDate;

public class DataManager {

	private Agent agent;
	private ArrayList<Message> messagesHistory;
	
	public DataManager(Agent agent) {
		this.agent = agent;
		this.messagesHistory = new ArrayList<Message>();
	}
	
	public ArrayList<Message> getMessagesHistory(){
		return messagesHistory;
	}
	
	public void updateMessagesHistory(Contact contact, String texte) {
		Message m = new Message(texte, LocalDate.now(),contact);
		
		// BDD
		this.messagesHistory.add(m);
		//
		
		agent.getInterfaceManager().getMainInterface();
		
	}
	
	
}
