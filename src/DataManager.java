import java.util.ArrayList;
import java.util.Date;
import java.lang.Object;

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
	
	public void updateMessagesHistory(Message message) {
		this.messagesHistory.add(message);
		
	}
	
	
}
