/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description   : This is a Helper Class for displaying Tutorial on View Part.
 * 					once data is received from Rest Api, all related data is group locally and sent back to view for displaying purposes.
 */

package com.spoken.Utility;
/**
 * A Utility class to store information of Tutorial referencing SPoken Tutorial
 * @author om Prakash
 *
 */
public class TutorialList {
	/**
	 * A unique Id 
	 */
	int tutorialId;
	
	/**
	 * Path to Video 
	 */
	String video_url;
	/**
	 * Path to video THumbnail
	 */
	String thumbnail_path;
	/**
	 * outline of tutorial
	 */
	String outline;
	/**
	 * Name of tutorial
	 */
	String tut_name;
	/**
	 * Name of Topic under which tutorial is added.
	 */
	String topicNAme;
	/**
	 * NAme of User who added this tutorial.
	 */
	String contributedBy;
	
	/**
	 * Visibility of Tutorial in application.
	 */
	int status;
	
	public String getVideo_url() {
		return video_url;
	}
	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}
	public String getThumbnail_path() {
		return thumbnail_path;
	}
	public void setThumbnail_path(String thumbnail_path) {
		this.thumbnail_path = thumbnail_path;
	}
	public String getOutline() {
		return outline;
	}
	public void setOutline(String outline) {
		this.outline = outline;
	}
	public String getTut_name() {
		return tut_name;
	}
	public void setTut_name(String tut_name) {
		this.tut_name = tut_name;
	}
	public int getTutorialId() {
		return tutorialId;
	}
	public void setTutorialId(int tutorialId) {
		this.tutorialId = tutorialId;
	}
	public String getTopicNAme() {
		return topicNAme;
	}
	public void setTopicNAme(String topicNAme) {
		this.topicNAme = topicNAme;
	}
	public String getContributedBy() {
		return contributedBy;
	}
	public void setContributedBy(String contributedBy) {
		this.contributedBy = contributedBy;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
}
