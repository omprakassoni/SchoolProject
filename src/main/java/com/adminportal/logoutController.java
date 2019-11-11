package com.adminportal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class logoutController {

	@RequestMapping(value = "/logoutAdmin",method = RequestMethod.GET)
	public String logoutGetAdmin(HttpServletRequest req) {
		
		HttpSession session=req.getSession(false);
		session.removeAttribute("UserLogedAdmin");
		session.removeAttribute("UserNameAdmin");
		
		
		if(session.getAttribute("UserLogedUserView")==null) {
			session.invalidate();
		}
		
		
		return "redirect:/";
		
	}
	
	@RequestMapping(value = "/logoutUser",method = RequestMethod.GET)
	public String logoutGetUser(HttpServletRequest req) {
		
		HttpSession session=req.getSession(false);
		session.removeAttribute("UserLogedUserView");
		session.removeAttribute("UserNameUserSide");
		
		if(session.getAttribute("UserLogedAdmin")==null) {
			session.invalidate();
		}
		
		
		return "redirect:/";
		
	}
}
