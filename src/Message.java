import java.time.LocalDate;

public class Message {

	private String message;
	private LocalDate timestamp;
	private Contact receiver;
	private Contact author;
	
	public Message(String message, LocalDate timestamp, Contact author, Contact receiver) {
		this.author = author;
		this.receiver = receiver;
		this.message = message;
		this.timestamp = timestamp;
	}

	public String toString() {
		return ("From: "+author.getPseudo()+" to: "+receiver.getPseudo()+" / message is: "+message);
	}
	
	public String getMessage() {
		return message;
	}

	public LocalDate getTimestamp() {
		return timestamp;
	}

	public Contact getReceiver() {
		return receiver;
	}

	public Contact getSelf() {
		return author;
	}
	
	public void setReceiver(Contact contact) {
		this.receiver = contact;
	}

	public Contact getAuthor() {
		return author;
	}

	public void setAuthor(Contact author) {
		this.author = author;
	}
	
	
	
}
