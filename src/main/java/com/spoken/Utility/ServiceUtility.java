/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */

package com.spoken.Utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import com.adminportal.HomeController;


public class ServiceUtility {
	
	private static final String SALT="salt";
	private static String uploadDirectory="src/main/resources/static"+"/Media/content/";
	
	public static BCryptPasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder(12,new SecureRandom(SALT.getBytes()));
	}
	
	public static Timestamp getCurrentTime() {
		
		Date date=new Date();
		long t=date.getTime();
		Timestamp st=new Timestamp(t);
		
		return st;
	}
	
	public static String daysDifference(Timestamp date) {
		
		Timestamp presentdate=getCurrentTime();
		long difference =Math.abs(date.getTime()-presentdate.getTime());
		long differenceDate=difference/(24*60*60*1000);
		
		
		return Long.toString(differenceDate);
	}
	
	
	
	public static boolean createclassSubjectFolder(String className,String subject,String topicName) {
		
		boolean status=true;
		if(!new File(uploadDirectory+className+"_"+subject+"/"+topicName+"/").exists()) {
			if(new File(uploadDirectory+className+"_"+subject+"/"+topicName+"/").mkdirs()) {
				
				new File(uploadDirectory+className+"_"+subject+"/"+topicName+"/"+"Document/").mkdirs();
				new File(uploadDirectory+className+"_"+subject+"/"+topicName+"/"+"Lessonplan/").mkdirs();
				new File(uploadDirectory+className+"_"+subject+"/"+topicName+"/"+"Quiz/").mkdirs();
				new File(uploadDirectory+className+"_"+subject+"/"+topicName+"/"+"ConceptMap/").mkdirs();
			}
		}
		return status;
	}
	
	public static boolean createFolder(String path) {
		boolean status=false;
		if(!new File(path).exists()) {
			status=new File(path).mkdirs();
		}
		return status;
		
	}
	
	public static String uploadFile(MultipartFile[] uploadFile,String pathToUpload) {
		String path=null;
		for(MultipartFile file:uploadFile) {
			Path fileNameAndPath =Paths.get(pathToUpload, file.getOriginalFilename());
		
			try {
				Files.write(fileNameAndPath, file.getBytes());
				System.out.println(fileNameAndPath.toString());
				path=fileNameAndPath.toString();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			}
		
		return path;
	}
}
