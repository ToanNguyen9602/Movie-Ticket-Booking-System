package com.demo.services;

public interface MailService {
	
	public boolean Send(String from, String to, String subject, String content);

}
