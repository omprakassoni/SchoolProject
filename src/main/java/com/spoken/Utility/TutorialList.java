/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description   : This is a Helper Class for displaying Tutorial on View Part.
 * 					once data is received from Rest Api, all related data is group locally and sent back to view for displaying purposes.
 */

package com.spoken.Utility;

public class TutorialList {

	int tutorialId;

	String video_url;
	String thumbnail_path;
	String outline;
	String tut_name;
	String topicNAme;
	String contributedBy;
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
