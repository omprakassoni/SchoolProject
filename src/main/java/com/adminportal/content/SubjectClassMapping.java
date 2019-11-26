/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : This modal is representation of many-many relation among Class and subject.
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="subject_class_mapping")
public class SubjectClassMapping {

	
	@Id
	@Column(name="sub_class_id",nullable = false,updatable = false)
	private int subClassId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="sub_id")
	private Subject sub;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="class_id")
	private Class standard;
	
	@OneToMany(mappedBy = "subjectClassMapping",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<Topic> topic=new HashSet<Topic>(); 
	
	
	public SubjectClassMapping() {
		
	}
	
	public SubjectClassMapping(int subClassID,Class standard,Subject sub) {
		this.subClassId=subClassID;
		this.standard=standard;
		this.sub=sub;
	}

	

	public int getSubClassId() {
		return subClassId;
	}

	public void setSubClassId(int subClassId) {
		this.subClassId = subClassId;
	}

	public Subject getSub() {
		return sub;
	}

	public void setSub(Subject sub) {
		this.sub = sub;
	}

	public Class getStandard() {
		return standard;
	}

	public void setStandard(Class standard) {
		this.standard = standard;
	}

	public Set<Topic> getTopic() {
		return topic;
	}

	public void setTopic(Set<Topic> topic) {
		this.topic = topic;
	}
	
	
	
}
