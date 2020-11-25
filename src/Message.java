import java.time.LocalDate;

public class Message {

	private String message;
	private LocalDate timestamp;
	
	public Message(String message, LocalDate timestamp) {
		this.message = message;
		this.timestamp = timestamp;
	}
}
