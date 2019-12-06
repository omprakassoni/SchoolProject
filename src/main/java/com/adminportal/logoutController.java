/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description   : This Class is a Controller Class
 * 					this class is responsible for handling all logout url
 */

package com.adminportal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class logoutController {

	
	/*  Method supporting session management in application, operation performed after user or admin press Logout
	 * 
	 */
	
	@RequestMapping(value = "/logout",method = RequestMethod.GET)   
	public String logoutGetAdmin(HttpServletRequest req) {
		
		HttpSession session=req.getSession(false);
		session.invalidate();
		
		
		return "redirect:/";
		
	}
	
//	@RequestMapping(value = "/logoutUser",method = RequestMethod.GET)
//	public String logoutGetUser(HttpServletRequest req) {
//		
//		HttpSession session=req.getSession(false);
//		session.removeAttribute("UserLogedUserView");
//		session.removeAttribute("UserNameUserSide");
//		
//		if(session.getAttribute("UserLogedAdmin")==null) {
//			session.invalidate();
//		}
//		
//		
//		return "redirect:/";
//		
//	}
}
