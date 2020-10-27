package com.adminportal.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.adminportal.domain.User;

/**
 * This class construct Email with all different property
 * @author om prakash
 *
 */
@Component
public class MailConstructor {
	
	/**
	 * This create simpleMail Template to send mail to user for retrieving password for their account.
	 * @param contextPath A full qualified Url 
	 * @param locale locale Object
	 * @param token A String consisting random set of string 
	 * @param user User Object
	 * @return SimpleMailMessage Object with inducted Configuration.
	 */
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
	
	/**
	 * This create simpleMail Template to send mail to user on getting approved for Teacher Role.
	 * @param usr User Object
	 * @return SimpleMailMessage Object with inducted Configuration.
	 */
	public SimpleMailMessage confirmOnApproveTeacher(User usr) {
		String message="Hello your account has been verified now you can login into our website";
		SimpleMailMessage email=new SimpleMailMessage();
		email.setSubject("Account Activated");
		email.setText(message);
		email.setTo(usr.getEmail());
		return email;
	}
}

