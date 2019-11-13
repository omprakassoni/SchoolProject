/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */

package com.adminportal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import com.spoken.Utility.ServiceUtility;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	
	private BCryptPasswordEncoder passwordEncoder() {
		return ServiceUtility.passwordEncoder();
	}
	
	private static final String[] PUBLIC_MATCHERS = {
			"/css/**",
			"/js/**",
			"/Images/**",
			"/img/**",
			"/Media/**",
			"/",
			"/Login",
			"/home",
			"/Signup",
			"/loadByClassName",
			"/loadByClassnameAndSubject",
			"/loadByUser",
			"/loadByValidity",
			"/loadBySubject",
			"/loadByClass",
			"/loadBySubjectClass",
			"/loadByTopic",
			"/loadByTopicDesc",
			"/loadByQuizQuestionID",
			"/loadByVideoID",
			"/loadByVideoIDSource",
			"/loadByArtcileID",
			"/loadByArtcileIDDesc",
			"/loadByDocumentID",
			"/loadByDocumentIDDesc",
			"/loadByphetID",
			"/loadByphetIDDesc",
			"/updateSubject",
			"/updateTopic",
			"/updateQuiz",
			"/updateDocument",
			"/updateVideo",
			"/updatePhet",
			"/updateArticle"
		
	};
	
	@Override
	protected void configure(HttpSecurity http)throws Exception{
		
//		http.authorizeRequests()
//			.antMatchers(PUBLIC_MATCHERS).permitAll()
//			.anyRequest().authenticated()
//			.and()
//			.formLogin()
//			.loginPage("/Login");
//
//		
//		
//		http
//		.csrf().disable().cors().disable();
		
	}


}
