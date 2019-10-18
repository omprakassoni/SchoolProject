/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */

package com.spoken.Utility;

public class SubjectAjaxQueryResolver {
	
	int id;
	String subName;
	String className;
	public SubjectAjaxQueryResolver() {}
	
	public SubjectAjaxQueryResolver(int id, String subName, String className) {
		super();
		this.id = id;
		this.subName = subName;
		this.className = className;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubName() {
		return subName;
	}
	public void setSubName(String subName) {
		this.subName = subName;
	}
	
	

}
