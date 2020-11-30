import java.time.LocalDate;

public class Message {

	private int idSrc;
	private int idDst;
	private String message;
	private LocalDate timestamp;
	
	public Message(int idSrc, int idDst, String message, LocalDate timestamp) {
		this.idSrc = idSrc;
		this.idDst = idDst;
		this.message = message;
		this.timestamp = timestamp;
	}

	public int getIdSrc() {
		return idSrc;
	}

	public int getIdDst() {
		return idDst;
	}

	public String getMessage() {
		return message;
	}

	public LocalDate getTimestamp() {
		return timestamp;
	}
	
	
}
