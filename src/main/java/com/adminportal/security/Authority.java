/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */

package com.adminportal.security;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority{

	private final String authority;
	
	public Authority(String authority) {
		this.authority=authority;
	}
	
	
	@Override
	public String getAuthority() {
		
		return authority;
	}

}
