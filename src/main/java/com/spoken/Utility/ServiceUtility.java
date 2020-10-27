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

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import com.adminportal.HomeController;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.util.Zip4jUtil;
/**
 * This class contains various utility function like creating password using Encoding scheme, getting current time, Checking extension for various format
 * Supported in application, Create various folder in application, Uploading file etc.
 * @author om prakash
 *
 */

public class ServiceUtility {
	
	/**
	 *  This is External path where all the resources related to application resides.
	 */
	static String mediaPath="/var/school_data/";
	
	/**
	 * This is used to create encoded password using text hashing.
	 */
	private static final String SALT="salt";
	
	/**
	 * This is starting path for all the resources adding with external path.
	 */
	private static String uploadDirectory="Media/content/";
	
	
	/**
	 * This method create random password.
	 * @return object to BCryptPasswordEncoder class.
	 */
	public static BCryptPasswordEncoder passwordEncoder() {										// password encoding
		
		return new BCryptPasswordEncoder(12,new SecureRandom(SALT.getBytes()));
	}
	
	
	/**
	 * This method is used to get current time.
	 * @return object to TimeStamp class
	 */
	public static Timestamp getCurrentTime() {								// Current Date
		
		Date date=new Date();
		long t=date.getTime();
		Timestamp st=new Timestamp(t);
		
		return st;
	}
	
	/**
	 * This method return number of days which is difference of current date and given date as a parameter.
	 * @param date Timestamp object  
	 * @return String object containing number of days as value.
	 */
	public static String daysDifference(Timestamp date) {						// days Difference Between 2 date(current - given)
		
		Timestamp presentdate=getCurrentTime();
		long difference =Math.abs(date.getTime()-presentdate.getTime());
		long differenceDate=difference/(24*60*60*1000);
		
		
		return Long.toString(differenceDate);
	}
	
	
	/**
	 * This method creates folder related to topic created in application based on given input in string format of various classes like Classname, Subject and Topic. 
	 * @param className String value containing ClassName
	 * @param subject	String value containing Subject ID
	 * @param topicName String value containing TopicName ID
	 * @return boolean value denoting true for Successful and false for failure.
	 * @throws Exception
	 */
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
	
	/**
	 * This method is used to create folder using the path given as input parameter
	 * @param path path to upload file
	 * @return boolean value denoting true for Successful and false for failure.
	 */
	public static boolean createFolder(String path) {					// check for existence of path
		boolean status=false;
		if(!new File(path).exists()) {
			status=new File(path).mkdirs();
		}
		return status;
		
	}
	
	/**
	 * This method is used to upload file in specified path given as input.
	 * @param uploadFile Array of MultipartFile Object to upload multiple file
	 * @param pathToUpload A String formatted path where file to be uploaded.
	 * @return relative path where file is uploaded or null if failed.
	 * @throws Exception
	 */
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
	
	/**
	 * Upload Zip formatted file in specified path given as input
	 * @param uploadFile Array of MultipartFile Object to upload multiple file
	 * @param pathToUpload A String formatted path where file to be uploaded.
	 * @return  relative path where file is uploaded or null if failed.
	 * @throws Exception
	 */
	public static String uploadZipFile(MultipartFile[] uploadFile,String pathToUpload) throws Exception{		// uploading file
		String path=null;	
		for(MultipartFile file:uploadFile) {
			Path fileNameAndPath =Paths.get(pathToUpload, file.getOriginalFilename());
				
				Files.write(fileNameAndPath, file.getBytes());
				System.out.println(fileNameAndPath.toString());
				path=fileNameAndPath.toString();
				
				
				File[] files1 = new File(pathToUpload).listFiles();
				for(File fileTemp:files1) {
					if(fileTemp.isFile() && !(fileTemp.getName().endsWith("zip") || fileTemp.getName().endsWith("ZIP"))) {
						fileTemp.delete();
					}else if(fileTemp.isDirectory()) {
						FileUtils.deleteDirectory(fileTemp);
					}
				}
				new ZipFile(fileNameAndPath.toString()).extractAll(pathToUpload);
				
				File[] files2 = new File(pathToUpload).listFiles();
				for(File filetemp:files2) {
					if(filetemp.isFile()) {
						if(filetemp.getName().endsWith(".html") || filetemp.getName().endsWith(".xhtml")) {
							path=pathToUpload+filetemp.getName();
							Files.delete(fileNameAndPath);
						}
					}
				}
			
			}
		
		return path;
	}
	
	/**
	 * Upload video file formatted in specified path given as argument.
	 * @param file  MultipartFile Object to upload file
	 * @param pathToUpload A String formatted path where file to be uploaded.
	 * @return relative path where file is uploaded or null if failed.
	 * @throws Exception
	 */
	public static String uploadVideoFile(MultipartFile file,String pathToUpload) throws Exception{		// uploading file
		String path=null;	
		
			Path fileNameAndPath =Paths.get(pathToUpload, file.getOriginalFilename());
			
				Files.write(fileNameAndPath, file.getBytes());
				System.out.println(fileNameAndPath.toString());
				path=fileNameAndPath.toString();
			
			
		
		return path;
	}
	
	/**
	 * Checks whether file extension is pdf irrespective of case or not?
	 * @param pdfFile Array of MutipartFile object with source file.
	 * @return validity against pdf file extension.
	 */
	public static boolean checkFileExtensionPDF(MultipartFile[] pdfFile) {				// validate file against PDF extension
		
		for(MultipartFile temp:pdfFile) {
			if(!temp.getOriginalFilename().endsWith(".pdf") && !temp.getOriginalFilename().endsWith(".PDF")) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks whether file extension is pdf irrespective of case or not?
	 * @param pdfFile MutipartFile object with source file.
	 * @return validity against pdf file extension.
	 */
	public static boolean checkFileExtensiononeFilePDF(MultipartFile pdfFile) {				// validate file against PDF extension
		
		
			if(!pdfFile.getOriginalFilename().endsWith(".pdf") && !pdfFile.getOriginalFilename().endsWith(".PDF")) {
				return false;
			}
		
		return true;
	}
	
	/**
	 * Checks whether file extension is jpg/jpeg/png irrespective of case or not?
	 * @param imageFile Array of MutipartFile object with source file.
	 * @return validity against image file extension.
	 */
	public static boolean checkFileExtensionImage(MultipartFile[] imageFile) {			// validate file against Image Extension
		
		for(MultipartFile temp:imageFile) {
			if(!temp.getOriginalFilename().endsWith(".jpg") && !temp.getOriginalFilename().endsWith(".jpeg") && !temp.getOriginalFilename().endsWith(".png")
					&& !temp.getOriginalFilename().endsWith(".JPG") && !temp.getOriginalFilename().endsWith(".JPEG") && !temp.getOriginalFilename().endsWith(".PNG")) {
				
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * checks whether file extension is mp4/mov irrespective of case or not ?
	 * @param videoFile MutipartFile object with source file.
	 * @return validity against video file extension.
	 */
	public static boolean checkFileExtensionVideo(MultipartFile videoFile) {			// validate file against Image Extension
		
		
		if(!videoFile.getOriginalFilename().endsWith(".mp4") && !videoFile.getOriginalFilename().endsWith(".mov")
				&& !videoFile.getOriginalFilename().endsWith(".MP4") && !videoFile.getOriginalFilename().endsWith(".MOV")) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * checks whether file extension is zip irrespective of case or not ?
	 * @param zipFile Array of MutipartFile object with source file.
	 * @return validity against Zip file extension.
	 */
	public static boolean checkFileExtensionZip(MultipartFile[] zipFile) {			// validate file against HTML Extension
		
		for(MultipartFile temp:zipFile) {
			if(!temp.getOriginalFilename().endsWith(".zip") && !temp.getOriginalFilename().endsWith(".ZIP")) {
				
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Returns current directory
	 * @return String value of currentpath.
	 */
	public static String presentDirectory() {
		Path currentRelativePath = Paths.get("");
		String currentpath = currentRelativePath.toAbsolutePath().toString();
		return currentpath;
		
	}
	
	/**
	 * Checks whether email is in proper format or not?
	 * @param email String value of email
	 * @return boolean value representing validity against Email Checks.
	 */
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
	
	/**
	 * Checks whether number exist in string or not?
	 * @param input String value
	 * @return boolean value true for no Number in String else false.
	 */
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
	
	/**
	 * this method is used for convert String in date object
	 * @param date String value representing date value.
	 * @return date object having parsed date value from input 
	 * @throws ParseException
	 */
	public static java.sql.Date convertStringToDate(String date) throws ParseException{
		SimpleDateFormat sd1=new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dateUtil=sd1.parse(date);
		return new java.sql.Date(dateUtil.getTime());
	}
}
