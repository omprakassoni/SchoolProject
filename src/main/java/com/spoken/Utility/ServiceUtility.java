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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import com.adminportal.HomeController;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.util.Zip4jUtil;


public class ServiceUtility {
	
	static String mediaPath="/var/school_data/";
	private static final String SALT="salt";
	private static String uploadDirectory="Media/content/";
	
	
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
	
	
	 
	public static boolean createclassSubjectFolder(String className,String subject,String topicName) throws Exception{  // creating folder for topic
		
		boolean status=true;
		if(!new File(mediaPath+uploadDirectory+className+"_"+subject+"/"+topicName+"/").exists()) {
			if(new File(mediaPath+uploadDirectory+className+"_"+subject+"/"+topicName+"/").mkdirs()) {
				
				new File(mediaPath+uploadDirectory+className+"_"+subject+"/"+topicName+"/"+"Document/").mkdirs();
				new File(mediaPath+uploadDirectory+className+"_"+subject+"/"+topicName+"/"+"Lessonplan/").mkdirs();
				new File(mediaPath+uploadDirectory+className+"_"+subject+"/"+topicName+"/"+"Quiz/").mkdirs();
				new File(mediaPath+uploadDirectory+className+"_"+subject+"/"+topicName+"/"+"ConceptMap/").mkdirs();
				new File(mediaPath+uploadDirectory+className+"_"+subject+"/"+topicName+"/"+"Video/").mkdirs();
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
	
	public static String uploadFile(MultipartFile[] uploadFile,String pathToUpload) throws Exception{		// uploading file
		String path=null;	
		for(MultipartFile file:uploadFile) {
			Path fileNameAndPath =Paths.get(pathToUpload, file.getOriginalFilename());
		
				Files.write(fileNameAndPath, file.getBytes());
				System.out.println(fileNameAndPath.toString());
				path=fileNameAndPath.toString();
			
			}
		
		return path;
	}
	
//	public static String uploadZipFile(MultipartFile[] uploadFile,String pathToUpload) throws Exception{		// uploading file
//		String path=null;	
//		for(MultipartFile file:uploadFile) {
//			Path fileNameAndPath =Paths.get(pathToUpload, file.getOriginalFilename());
//				
//				Files.write(fileNameAndPath, file.getBytes());
//				System.out.println(fileNameAndPath.toString());
//				path=fileNameAndPath.toString();
//				
//				new ZipFile(fileNameAndPath.toString()).extractAll(pathToUpload);
//				File[] files = new File(pathToUpload).listFiles();
//				for(File filetemp:files) {
//					if(filetemp.isFile()) {
//						if(filetemp.getName().endsWith(".html") || filetemp.getName().endsWith(".xhtml")) {
//							path=pathToUpload+filetemp.getName();
//							Files.delete(fileNameAndPath);
//						}
//					}
//				}
//			
//			}
//		
//		return path;
//	}
	
	public static String uploadVideoFile(MultipartFile file,String pathToUpload) throws Exception{		// uploading file
		String path=null;	
		
			Path fileNameAndPath =Paths.get(pathToUpload, file.getOriginalFilename());
			
				Files.write(fileNameAndPath, file.getBytes());
				System.out.println(fileNameAndPath.toString());
				path=fileNameAndPath.toString();
			
			
		
		return path;
	}
	
	public static boolean checkFileExtensionPDF(MultipartFile[] pdfFile) {				// validate file against PDF extension
		
		for(MultipartFile temp:pdfFile) {
			if(!temp.getOriginalFilename().endsWith(".pdf") && !temp.getOriginalFilename().endsWith(".PDF")) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean checkFileExtensiononeFilePDF(MultipartFile pdfFile) {				// validate file against PDF extension
		
		
			if(!pdfFile.getOriginalFilename().endsWith(".pdf") && !pdfFile.getOriginalFilename().endsWith(".PDF")) {
				return false;
			}
		
		return true;
	}
	
	
	public static boolean checkFileExtensionImage(MultipartFile[] imageFile) {			// validate file against Image Extension
		
		for(MultipartFile temp:imageFile) {
			if(!temp.getOriginalFilename().endsWith(".jpg") && !temp.getOriginalFilename().endsWith(".jpeg") && !temp.getOriginalFilename().endsWith(".png")
					&& !temp.getOriginalFilename().endsWith(".JPG") && !temp.getOriginalFilename().endsWith(".JPEG") && !temp.getOriginalFilename().endsWith(".PNG")) {
				
				return false;
			}
		}
		return true;
	}
	
	public static boolean checkFileExtensionVideo(MultipartFile videoFile) {			// validate file against Image Extension
		
		
		if(!videoFile.getOriginalFilename().endsWith(".mp4") && !videoFile.getOriginalFilename().endsWith(".mov")
				&& !videoFile.getOriginalFilename().endsWith(".MP4") && !videoFile.getOriginalFilename().endsWith(".MOV")) {
			return false;
		}
		
		return true;
	}
	
	public static boolean checkFileExtensionHtml(MultipartFile[] imageFile) {			// validate file against HTML Extension
		
		for(MultipartFile temp:imageFile) {
			if(!temp.getOriginalFilename().endsWith(".html") && !temp.getOriginalFilename().endsWith(".xhtml")) {
				
				return false;
			}
		}
		return true;
	}
	
	
	
	public static String presentDirectory() {
		Path currentRelativePath = Paths.get("");
		String currentpath = currentRelativePath.toAbsolutePath().toString();
		return currentpath;
		
	}
	
	public static boolean checkEmailValidity(String email) {

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            return false; 
        
        return pat.matcher(email).matches(); 
	}
	
	public static boolean checkContainNumeralInString(String input) {
		for(int i=0 ; i<input.length();i++) {
			if(input.charAt(i)=='0' || input.charAt(i)=='1' || input.charAt(i)=='2' || input.charAt(i)=='3' ||
					input.charAt(i)=='4' || input.charAt(i)=='5' || input.charAt(i)=='6' || input.charAt(i)=='7' ||
					input.charAt(i)=='8' || input.charAt(i)=='9') {
				return false;
			}
		}
		return true;
	}
	
	public static java.sql.Date convertStringToDate(String date) throws ParseException{
		SimpleDateFormat sd1=new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dateUtil=sd1.parse(date);
		return new java.sql.Date(dateUtil.getTime());
	}
}
