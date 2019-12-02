/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description   : This is Utility class where various task is being carried out like
 * 					1. Password Encoding using fixed set of letter
 * 					2. operation to get Current date 
 * 					3. operation find out days between 2 date
 * 					4. creating folder for Storing resources.
 * 					5. uploading file
 * 					6. checking Session
 *				
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

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import com.adminportal.HomeController;


public class ServiceUtility {
	
	private static final String SALT="salt";
	private static String uploadDirectory="/static"+"/Media/content/";
	
	public static BCryptPasswordEncoder passwordEncoder() {										// password encoding
		
		return new BCryptPasswordEncoder(12,new SecureRandom(SALT.getBytes()));
	}
	
	public static Timestamp getCurrentTime() {								// Current Date
		
		Date date=new Date();
		long t=date.getTime();
		Timestamp st=new Timestamp(t);
		
		return st;
	}
	
	public static String daysDifference(Timestamp date) {						// days Difference Between 2 date(current - given)
		
		Timestamp presentdate=getCurrentTime();
		long difference =Math.abs(date.getTime()-presentdate.getTime());
		long differenceDate=difference/(24*60*60*1000);
		
		
		return Long.toString(differenceDate);
	}
	
	
	 
	public static boolean createclassSubjectFolder(String className,String subject,String topicName) {  // creating folder for topic
		
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
	
	public static boolean createFolder(String path) {					// check for existence of path
		boolean status=false;
		if(!new File(path).exists()) {
			status=new File(path).mkdirs();
		}
		return status;
		
	}
	
	public static String uploadFile(MultipartFile[] uploadFile,String pathToUpload) {		// uploading file
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
	
	
	public static boolean checkFileExtensionPDF(MultipartFile[] pdfFile) {				// validate file against PDF extension
		
		for(MultipartFile temp:pdfFile) {
			if(!temp.getOriginalFilename().endsWith(".pdf")) {
				return false;
			}
		}
		return true;
	}
	
	
	public static boolean checkFileExtensionImage(MultipartFile[] imageFile) {			// validate file against Image Extension
		
		for(MultipartFile temp:imageFile) {
			if(!temp.getOriginalFilename().endsWith(".jpg") && !temp.getOriginalFilename().endsWith(".jpeg")) {
				
				return false;
			}
		}
		return true;
	}
	
	public static boolean chechExistSessionAdmin(HttpSession session) {					// validate Exist session for admin
		
		boolean status=true;
		if(session==null) {
			status=false;
		}else {
			if(session.getAttribute("UserLogedAdmin")==null) {
				status= false;
				
			}
		}
		
		return status;
		
	}
	
	public static boolean chechExistSessionUser(HttpSession session) {					// validate exist session for user
		
		boolean status=true;
		if(session==null) {
			status=false;
		}else {
			if(session.getAttribute("UserLogedUserView")==null) {
				status= false;
				
			}
		}
		
		return status;
		
	}
	
}
