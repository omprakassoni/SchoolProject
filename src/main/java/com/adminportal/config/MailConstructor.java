package com.adminportal.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.adminportal.domain.User;

@Component
public class MailConstructor {
	
	
	public SimpleMailMessage constructResetTokenEmail(
			String contextPath, Locale locale, String token, User user
			) {
		
		String url = contextPath + "/reset?token="+token;
		String message = "\nPlease click on this link to Update your password";
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject("Reset Password");
		email.setText(url+message);
		/* email.setFrom(env.getProperty("support.email")); */
		return email;
		
	}
	
	public SimpleMailMessage confirmOnApproveTeacher(User usr) {
		String message="Hello your account has been verified now you can login into our website";
		SimpleMailMessage email=new SimpleMailMessage();
		email.setSubject("Account Activated");
		email.setText(message);
		email.setTo(usr.getEmail());
		return email;
	}
}

