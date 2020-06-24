/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	description	  : User related information will get persist into database various user as such
 * 					1.Learner
 * 					2.Parent
 * 					3.Teacher
 * 					4.Admin
 *  
 */

package com.adminportal.domain;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.Comment;
import com.adminportal.content.CommentReply;
import com.adminportal.content.ConceptMap;
import com.adminportal.content.DocumentExternal;
import com.adminportal.content.LessonPlan;
import com.adminportal.content.Phets;
import com.adminportal.content.QuizQuestion;
import com.adminportal.content.Tutorial;
import com.adminportal.content.VideoExternal;
import com.adminportal.security.Authority;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.adminportal.content.Class;
import com.adminportal.content.Subject;
import com.adminportal.content.Topic;;


@Entity
@Table(name="user_details")
public class User implements UserDetails{
	
	@Id
	@Column(name = "user_id", nullable = false,updatable = false)
	private int id;
	
	@Column(name = "email", nullable = false,updatable = false)
	private String email;
	
	@Column(name="password" ,nullable = false)
	private String password;
	
	@Column(nullable = false,updatable = false)
	private String sex;
	
	@Column(name = "first_name", nullable = false)
	private String fname;
	
	@Column(name = "last_name", nullable = false)
	private String lname;

	
	@Column(name="document_path")
	private String document;
	
	@Column(name="School_name")
	private String schoolName;
	
	@Column(name="School_address")
	private String schoolAddress;
	
	@Column(name="pincode")
	private int pincode;
	@Column(name="date_added",nullable = false,updatable = false)
	private Timestamp dateAdded;
	
	@Column(name="last_login",nullable = false)
	private Timestamp lastLogin;
	
	@Column(name="dob")
	private Date dateOfBirth;
	
	@Column (name="isvalid",nullable = false )
	private int Registered;
	
	@Column(name="token")
	private String token;
	
	@Column(name="profilePic")
	private String profilePic;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<UserRole> userRoles=new ArrayList<UserRole>();
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<Class> classDb=new HashSet<Class>();
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<Subject> subjectDb=new HashSet<Subject>();
	
	@OneToMany(mappedBy = "userId",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<Topic> topic=new HashSet<Topic>();
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<ArticleExternal> articleExternal=new HashSet<ArticleExternal>();
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<DocumentExternal> documentExternal=new HashSet<DocumentExternal>();
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<LessonPlan> lessonPlan=new HashSet<LessonPlan>();
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<Phets> phet=new HashSet<Phets>();
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<QuizQuestion> quiz=new HashSet<QuizQuestion>();
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<VideoExternal> videoExternal=new HashSet<VideoExternal>();
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<Tutorial> tutorial=new HashSet<Tutorial>();
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<ConceptMap> conceptMap=new HashSet<ConceptMap>();
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<Comment> comment=new HashSet<Comment>();
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<CommentReply> commentReply=new HashSet<CommentReply>();
	
	
	public int getId() {
		return id;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public Set<ConceptMap> getConceptMap() {
		return conceptMap;
	}

	public void setConceptMap(Set<ConceptMap> conceptMap) {
		this.conceptMap = conceptMap;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolAddress() {
		return schoolAddress;
	}

	public void setSchoolAddress(String schoolAddress) {
		this.schoolAddress = schoolAddress;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public Timestamp getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Timestamp getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getRegistered() {
		return Registered;
	}

	public void setRegistered(int registered) {
		Registered = registered;
	}

	public Set<ArticleExternal> getArticleExternal() {
		return articleExternal;
	}

	public void setArticleExternal(Set<ArticleExternal> articleExternal) {
		this.articleExternal = articleExternal;
	}

	public Set<DocumentExternal> getDocumentExternal() {
		return documentExternal;
	}

	public void setDocumentExternal(Set<DocumentExternal> documentExternal) {
		this.documentExternal = documentExternal;
	}

	public Set<LessonPlan> getLessonPlan() {
		return lessonPlan;
	}

	public void setLessonPlan(Set<LessonPlan> lessonPlan) {
		this.lessonPlan = lessonPlan;
	}

	public Set<Phets> getPhet() {
		return phet;
	}

	public void setPhet(Set<Phets> phet) {
		this.phet = phet;
	}

	public Set<QuizQuestion> getQuiz() {
		return quiz;
	}

	public void setQuiz(Set<QuizQuestion> quiz) {
		this.quiz = quiz;
	}

	public Set<VideoExternal> getVideoExternal() {
		return videoExternal;
	}

	public void setVideoExternal(Set<VideoExternal> videoExternal) {
		this.videoExternal = videoExternal;
	}

	public Set<Comment> getComment() {
		return comment;
	}

	public void setComment(Set<Comment> comment) {
		this.comment = comment;
	}
	
	public Set<CommentReply> getCommentReply() {
		return commentReply;
	}

	public void setCommentReply(Set<CommentReply> commentReply) {
		this.commentReply = commentReply;
	}

	public Set<Tutorial> getTutorial() {
		return tutorial;
	}

	public void setTutorial(Set<Tutorial> tutorial) {
		this.tutorial = tutorial;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Set<Class> getClassDb() {
		return classDb;
	}

	public void setClassDb(Set<Class> classDb) {
		this.classDb = classDb;
	}

	public Set<Subject> getSubjectDb() {
		return subjectDb;
	}

	public void setSubjectDb(Set<Subject> subjectDb) {
		this.subjectDb = subjectDb;
	}

	public Set<Topic> getTopic() {
		return topic;
	}

	public void setTopic(Set<Topic> topic) {
		this.topic = topic;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Set<GrantedAuthority> authorities=new HashSet<GrantedAuthority>();
		for(UserRole x:userRoles) {
			authorities.add(new Authority(x.getRole().getRoleName()));
			System.out.println(x.getRole().getRoleName());
		}
		
		return authorities;
	}

	
	
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
	
		if(Registered==1) {
			return true;
		}else {
			return false;
		}
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", sex=" + sex + ", fname=" + fname
				+ ", lname=" + lname + ", document=" + document + ", schoolName=" + schoolName + ", schoolAddress="
				+ schoolAddress + ", pincode=" + pincode + ", dateAdded=" + dateAdded + ", lastLogin=" + lastLogin
				+ ", dateOfBirth=" + dateOfBirth + ", Registered=" + Registered + ", token=" + token + ", profilePic="
				+ profilePic + ", userRoles=" + userRoles + ", classDb=" + classDb + ", subjectDb=" + subjectDb
				+ ", topic=" + topic + ", articleExternal=" + articleExternal + ", documentExternal=" + documentExternal
				+ ", lessonPlan=" + lessonPlan + ", phet=" + phet + ", quiz=" + quiz + ", videoExternal="
				+ videoExternal + ", tutorial=" + tutorial + ", conceptMap=" + conceptMap + ", comment=" + comment
				+ ", commentReply=" + commentReply + "]";
	}

	
	

	
	

}
