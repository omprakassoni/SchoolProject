/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 *  Description   : This class is used for setting up the basic property under Spring security framework like
 *  				1. Authentication
 *  				2. Authorization
 *  				3. Login
 *  				4. Logout
 *  				5. Security on various Pages.
 *  				Not in Use Now, but will come into picture once Log-in and Log-out made it from Spring Security framework.
 */

package com.adminportal.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.adminportal.service.impl.UserSecurityService;
import com.spoken.Utility.ServiceUtility;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private Environment env;
	
	@Autowired
	private UserSecurityService	 userSecurityService;
	
	private BCryptPasswordEncoder passwordEncoder() {
		return ServiceUtility.passwordEncoder();
	}
	
	private static final String[] PUBLIC_MATCHERS = {
			"/css/**",
			"/js/**",
			"/Images/**",
			"/img/**",
			"/js/**",
			"/static/**",
			"/font/**",
			"/",
			"/datatables.min.js",
			"/datatables.min.css",
			"/webfonts/**",
			"/Login",
			"/courses",
			"/Signup",
			"/files/**",
			"/loadByClassName",
			"/loadBySubjectName",
			"/validateEmail",
			"/newUserP",
			"/newUserL",
			"/newUserT",
			"/content/**",
			"/contentTutorial/**",
			"/contentLesson/**",
			"/contentPhet/**",
			"/contentQuiz/**",
			"/contentLink/**",
			"/contentConcept/**",
			"/addContactForm",
			"/eventsList",
			"/testimonials",
			
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
	
	public static final String[] CONTRIBUTOR_URL= {
			"/conVideo",
			"/conDocument",
			"/conArticle",
			"/conQuiz",
			"/conPhet",
			"/conLessonPlan",
			"/conConceptMap",
			"/conView"
	};
	
	@Override
	protected void configure(HttpSecurity http)throws Exception{
		
		http.authorizeRequests()
			.antMatchers("/admin/**").hasAnyAuthority("Admin")
			.antMatchers(CONTRIBUTOR_URL).hasAnyAuthority("Teacher")
			.antMatchers(PUBLIC_MATCHERS).permitAll()
			.anyRequest().authenticated();
		
		http.
			formLogin().failureUrl("/Login?error").defaultSuccessUrl("/")
			.loginPage("/Login").permitAll()
			.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/?logout").deleteCookies("remember-me").permitAll()
			.and()
			.rememberMe();

	
		
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
	}
	


}
