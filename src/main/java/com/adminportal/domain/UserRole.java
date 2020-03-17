/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : this is a representation of many to many relationship among user and user role.
 */

package com.adminportal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="user_role")
public class UserRole {
	
	
	@Id
	@Column(nullable = false,updatable = false)
	private int userRoleId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="role_id")
	private RoleDetail role;
	
	public UserRole() {
		
	}
	
	public UserRole(int userRoleId,User user,RoleDetail role) {
		this.userRoleId=userRoleId;
		this.user=user;
		this.role=role;
		
	}

	public int getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public RoleDetail getRole() {
		return role;
	}

	public void setRole(RoleDetail role) {
		this.role = role;
	}
	
	
	

}
