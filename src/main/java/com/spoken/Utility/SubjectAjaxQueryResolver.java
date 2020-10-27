/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description   : This is a Helper Class to fetch Subject data Related data.
 */

package com.spoken.Utility;

/**
 * A Utility class to take data of subjectClass Object on ajax call
 * @author om Prakash
 *
 */
public class SubjectAjaxQueryResolver {
	/**
	 * A Unique Id 
	 */
	int id;
	/**
	 * Subject NAme
	 */
	String subName;
	/**
	 * Class Name
	 */
	String className;
	
	/**
	 * Default Constructor
	 */
	public SubjectAjaxQueryResolver() {}
	
	/**
	 * Parameterized Constructor
	 * @param id A unique Id
	 * @param subName name of subject
	 * @param className name of Class
	 */
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
