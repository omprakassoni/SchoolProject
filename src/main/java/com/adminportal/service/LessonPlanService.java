/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Lesson interacting with its repository for database operation
 */

package com.adminportal.service;

import java.sql.Timestamp;
import java.util.List;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.ConceptMap;
import com.adminportal.content.DocumentExternal;
import com.adminportal.content.LessonPlan;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;

/**
 * This interface has all the method declaration related to LessonPlan database operation
 * @author om prakash
 *
 */
public interface LessonPlanService {
	
	/**
	 * Lists out all the LessonPlan object from database
	 * @return List of LessonPlan object
	 */
	List<LessonPlan> findAll();
	
	/**
	 * Delete lessonPlan object from database
	 * @param id LessonPlan ID
	 */
	void deleteLessonById(int id);
	
	/**
	 * Count total number lessonPlan object in database
	 * @return integer
	 */
	int countRow();
	
	/**
	 * update lessonPlan visibility flag given lessonplan ID
	 * @param status status value to be set
	 * @param id LessonPlan ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean EnableLessonPlanContent(int status,int id);
	
	/**
	 * Find LessonPlan object given lessonPlan ID
	 * @param id LessonPlan ID
	 * @return LessonPlan object
	 */
	LessonPlan findById(int id);
	
	/**
	 * Find list of LessonPlan object given Topic object
	 * @param topic Topic Object
	 * @return List of LessonPlan object
	 */
	List<LessonPlan> findAllByTopic(Topic topic) ;
	
	/**
	 * Find list of LessonPlan object given user object
	 * @param usr A USer object
	 * @return List of LessonPlan object
	 */
	List<LessonPlan> findALlByUser(User usr) ;

	/**
	 * Update LesonPlan's file path, modified date value given LessonPlan ID
	 * @param dat current timestamp 
	 * @param lessonPath file path
	 * @param id LessonPlan ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean updateLessonPlan(Timestamp dat,String lessonPath,int id);
	
	/**
	 * Update LesonPlan's acceptedByAdmin value given LessonPlan ID
	 * @param status status value to be set
	 * @param id LessonPlan ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean EnableAcceptedByAdminLessonPlanContent(int status,int id) ;
	
	/**
	 * Find list of LessonPlan object given Topic object and status value
	 * @param topic Topic object
	 * @return List of LessonPlan object
	 */
	List<LessonPlan> findAllByTopicAndStatus(Topic topic);
	
	/**
	 * persist lessonplan object into database
	 * @param temp LessonPlan Object
	 * @return
	 */
	int save(LessonPlan temp);
	
	/**
	 * Delete lessonPlan object from database
	 * @param lesson lessonPlan object
	 */
	void deleteLessonPlan(LessonPlan lesson);
}
