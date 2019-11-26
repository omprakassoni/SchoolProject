package com.spoken.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.adminportal.domain.User;

@Service
public class MailConstructor {
	
	private JavaMailSender javaMailSender;
//	
//	@Autowired
//	public  MailConstructor(JavaMailSender javaMailSender) {
//		this.javaMailSender=javaMailSender;
//	}
//	
	public void sendNotification(User user) throws MailException{
		SimpleMailMessage mail=new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom("omprakassoni@gmail.com");
		mail.setSubject("hello world");
		mail.setText("hello");
		
		javaMailSender.send(mail);
	}


}
