package model;

public class MessageBuff 
{
	private String officerId;
	private StringBuilder buff;

	public MessageBuff() {
		buff=new StringBuilder();
	}	
	
	public MessageBuff(String officerId, String newMessage) {
		this();
		this.officerId=officerId;
		buff.append(newMessage);
		
	}

	public String getOfficerId() {
		return officerId;
	}

	public void setOfficerId(String officerId) {
		this.officerId = officerId;
	}

	public String getBuffContent() {
		return buff.toString();
	}

	public void setBuffContent(String newMessage) {
		this.buff.append(newMessage);
	}

	public void clearBuff() {
		buff.delete(0, buff.length());
		
	}

}
