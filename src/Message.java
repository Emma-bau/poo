import java.time.LocalDate;

public class Message {

	private String message;
	private LocalDate timestamp;
	private Contact contact;
	
	public Message( String message, LocalDate timestamp, Contact contact) {
		this.self = 
		this.contact = contact;
		this.message = message;
		this.timestamp = timestamp;
	}

	
	public String getMessage() {
		return message;
	}

	public LocalDate getTimestamp() {
		return timestamp;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
	
}
