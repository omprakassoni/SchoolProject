/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Subject Modal to Store subject related infor into database.
 */

package com.adminportal.content;

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

@Entity
@Table(name="Subject")
public class Subject {
	
	@TableGenerator(name = "sub_gen", table = "id_gen", pkColumnName = "gen_name", valueColumnName = "gen_val", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE,generator = "sub_gen")
	@Column(nullable = false,updatable = false)
	private int subId;
	
	@Column(name="sub_name",nullable = false)
	private String subName;
	
	

	@OneToMany(mappedBy ="sub",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<SubjectClassMapping> subClasMapp=new HashSet<SubjectClassMapping>();

	

	
	
	public int getSubId() {
		return subId;
	}

	public void setSubId(int subId) {
		this.subId = subId;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public Set<SubjectClassMapping> getSubClasMapp() {
		return subClasMapp;
	}

	public void setSubClasMapp(Set<SubjectClassMapping> subClasMapp) {
		this.subClasMapp = subClasMapp;
	}

}
