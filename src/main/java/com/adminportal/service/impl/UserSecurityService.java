package com.adminportal.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.adminportal.domain.User;
import com.adminportal.repository.UserRepository;

/**
 * Default implementation of the {@link com.adminportal.service.org.springframework.security.core.userdetails.UserDetailsService} interface.  
 * @author om prakash
 *
 */
@Service
public class UserSecurityService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	/**
	 * @see com.adminportal.service.org.springframework.security.core.userdetails.UserDetailsService# loadUserByUsername(String username)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user=userRepo.findByemail(username);
		
		
		if(user==null) {
			throw new UsernameNotFoundException("E-mail Doesn't Exist");
		}
		
		return user;
	}

}
