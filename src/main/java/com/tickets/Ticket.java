package com.tickets;

import java.util.Collection;
import java.util.Map;

public class Ticket {
	private String customerName;
	private String subject;
	private String body;
	private Map<String, Attachment> attachements;
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Map<String, Attachment> getAttachements() {
		return attachements;
	}
	public void setAttachement(Attachment atachment) {
		this.attachements.put(atachment.getName(), atachment);
	}
	
	public int getNumberOfAttachments() {
		return this.attachements.size();
	}
	
	public void addAttachment(Attachment attachment) {
		attachements.put(attachment.getName(), attachment);
	}
	
	public Attachment getAttachment(String name) {
		return this.attachements.get(name);
	}
	
	public Collection<Attachment> getAttachments(){
		return this.attachements.values();
	}
}
