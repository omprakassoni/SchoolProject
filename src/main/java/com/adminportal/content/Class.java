/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="Class")
public class Class {
	
	@TableGenerator(name = "class_gen", table = "id_gen", pkColumnName = "gen_name", valueColumnName = "gen_val", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE,generator = "class_gen")
	@Column(nullable = false,updatable = false)
	private int class_id;
	
	@Column(name="class_name",nullable = false)
	private String className;
	
	@OneToMany(mappedBy = "standard",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<SubjectClassMapping> subClasMapp=new HashSet<SubjectClassMapping>();
	
	
	
	public Set<SubjectClassMapping> getSubClasMapp() {
		return subClasMapp;
	}
	public void setSubClasMapp(Set<SubjectClassMapping> subClasMapp) {
		this.subClasMapp = subClasMapp;
	}
	
	public int getClass_id() {
		return class_id;
	}
	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	

}
