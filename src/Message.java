import java.time.LocalDate;

public class Message {

	private String message;
	private LocalDate timestamp;
	private Contact contact;
	private Contact author;
	
	public Message(String message, LocalDate timestamp,/* Contact author, */ Contact contact) {
		//this.author = author;
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

	public Contact getSelf() {
		return author;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
	
	
}
