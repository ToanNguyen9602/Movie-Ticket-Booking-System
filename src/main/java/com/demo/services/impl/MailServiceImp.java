package com.demo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.demo.services.MailService;

import jakarta.mail.internet.MimeMessage;

@Service
public class MailServiceImp implements MailService {
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public boolean Send(String from, String to, String subject, String content) {
		try {
			MimeMessage mimeMessage= javaMailSender.createMimeMessage();
			MimeMessageHelper helper= new MimeMessageHelper(mimeMessage);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content, true);
			javaMailSender.send(mimeMessage);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
