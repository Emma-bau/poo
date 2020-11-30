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
	
	public void updateMessagesHistory(int idSrc, int idDst, String texte) {
		Message m = new Message(idSrc, idDst, texte, LocalDate.now());
		this.messagesHistory.add(m);
	}
	
	
}
