package model;
import java.time.LocalDate;


public class Message {
	
	/*----------------------------------------------------- Variable ----------------------------------------*/

	private String message;
	private LocalDate date;
	private int hour, minute;
	private Contact receiver, author;


	/*----------------------------------------------------- Constructor ----------------------------------------*/
	public Message(String message, LocalDate date, int hour, int minute, Contact author, Contact receiver) {
		this.author = author;
		this.receiver = receiver;
		this.message = message;
		this.date = date;
		this.hour = hour;
		this.minute = minute;
	}
	
	/*----------------------------------------------------- Getters and setters ----------------------------------------*/

	public String toString() {
		return ("From: "+author.getPseudo()+" to: "+receiver.getPseudo()+" / message is: "+message);
	}
	
	public String getMessage() {
		return message;
	}

	public LocalDate getDate() {
		return date;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
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
