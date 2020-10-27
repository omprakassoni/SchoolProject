/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Representation of role details available into database.
 */

package com.adminportal.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * This class Stores different Role in system.
 * @author om
 *
 */
@Entity
@Table(name="role_details")
public class RoleDetail {
	
	/**
	 * A unique ID
	 */
	@Id
	@Column(name = "role_id", nullable = false,updatable = false)
	private int roleId;
	
	/**
	 * RoleNAme 
	 */
	@Column(nullable = false)
	private String roleName;
	
	@OneToMany(mappedBy = "role",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<UserRole> userRoles=new HashSet<UserRole>();

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	
}
