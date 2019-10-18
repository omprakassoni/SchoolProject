package com.spoken.Utility;

public class CommentAjaxQueryResolver {
	
	int comId;
	String comment;
	String userName;
	String dateReceived;
	
	public CommentAjaxQueryResolver() {}
	
	
	
	public CommentAjaxQueryResolver(int comId, String comment, String userName, String dateReceived) {
		
		this.comId = comId;
		this.comment = comment;
		this.userName = userName;
		this.dateReceived = dateReceived;
	}



	public int getComId() {
		return comId;
	}
	public void setComId(int comId) {
		this.comId = comId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDateReceived() {
		return dateReceived;
	}
	public void setDateReceived(String dateReceived) {
		this.dateReceived = dateReceived;
	}
	
	

}
