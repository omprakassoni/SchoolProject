/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Topic Modal to persist same into database.
 */

package com.adminportal.content;

import java.sql.Timestamp;
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
@Table(name="Topic")
public class Topic {
	
	@TableGenerator(name = "topic_gen", table = "id_gen", pkColumnName = "gen_name", valueColumnName = "gen_val", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE,generator = "sub_gen")
	@Column(name="topic_id",updatable = false)
	private int topicId;
	
	@Column(name="topic_name",nullable = false)
	private String topicName;
	
	@Column(name="poster",nullable = false)
	private String poster;
	
	@Column(name="description",nullable = false,length = 5000)
	private String description;
	
	@Column(name="date_added",updatable = false,nullable = false)
	private Timestamp dateAdded;
	
	@Column(name="date_modified",nullable = false)
	private Timestamp dateModified;
	
	@Column(name="status",nullable = false)
	private int status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="subClassId")
	private SubjectClassMapping subjectClassMapping;
	
	@OneToMany(mappedBy = "topic",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<QuizQuestion> quizQuestion=new HashSet<QuizQuestion>();
	
	@OneToMany(mappedBy = "topic",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<VideoExternal> videoExternal=new HashSet<VideoExternal>();
	
	@OneToMany(mappedBy = "topic",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<DocumentExternal> documentExternal=new HashSet<DocumentExternal>();
	
	@OneToMany(mappedBy = "topic",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Phets> phets=new HashSet<Phets>();
	
	@OneToMany(mappedBy = "topic",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<LessonPlan> lessonPlan=new HashSet<LessonPlan>();
	
	@OneToMany(mappedBy = "topic",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ArticleExternal> articleExternal=new HashSet<ArticleExternal>();
	
	@OneToMany(mappedBy = "topic",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<Tutorial> tutorial=new HashSet<Tutorial>();
	
	@OneToMany(mappedBy = "topic",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<ConceptMap> conceptMap=new HashSet<ConceptMap>();

	public int getTopicId() {
		return topicId;
	}

	public Set<ConceptMap> getConceptMap() {
		return conceptMap;
	}

	public void setConceptMap(Set<ConceptMap> conceptMap) {
		this.conceptMap = conceptMap;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Timestamp getDateModified() {
		return dateModified;
	}

	public void setDateModified(Timestamp dateModified) {
		this.dateModified = dateModified;
	}

	public SubjectClassMapping getSubjectClassMapping() {
		return subjectClassMapping;
	}

	public void setSubjectClassMapping(SubjectClassMapping subjectClassMapping) {
		this.subjectClassMapping = subjectClassMapping;
	}

	public Set<QuizQuestion> getQuizQuestion() {
		return quizQuestion;
	}

	public void setQuizQuestion(Set<QuizQuestion> quizQuestion) {
		this.quizQuestion = quizQuestion;
	}

	public Set<VideoExternal> getVideoExternal() {
		return videoExternal;
	}

	public void setVideoExternal(Set<VideoExternal> videoExternal) {
		this.videoExternal = videoExternal;
	}

	public Set<DocumentExternal> getDocumentExternal() {
		return documentExternal;
	}

	public void setDocumentExternal(Set<DocumentExternal> documentExternal) {
		this.documentExternal = documentExternal;
	}

	public Set<Phets> getPhets() {
		return phets;
	}

	public void setPhets(Set<Phets> phets) {
		this.phets = phets;
	}

	public Set<LessonPlan> getLessonPlan() {
		return lessonPlan;
	}

	public void setLessonPlan(Set<LessonPlan> lessonPlan) {
		this.lessonPlan = lessonPlan;
	}

	public Set<ArticleExternal> getArticleExternal() {
		return articleExternal;
	}

	public void setArticleExternal(Set<ArticleExternal> articleExternal) {
		this.articleExternal = articleExternal;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Set<Tutorial> getTutorial() {
		return tutorial;
	}

	public void setTutorial(Set<Tutorial> tutorial) {
		this.tutorial = tutorial;
	}
	
	
	
}
