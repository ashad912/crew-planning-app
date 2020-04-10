package pl.jee.klos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pl.jee.klos.service2.CrewMemberService;
import pl.jee.klos.service2.PlanningMemberService;

@Controller
public class AdminController
{
	@Autowired
	CrewMemberService crewMemberService;
	
	@Autowired
	PlanningMemberService planningMemberService;
	
	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() 
	{
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Hello World");
		model.addObject("message", "This is protected page!");
		model.setViewName("admin");
		
		return model;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
		@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout) {
 
		ModelAndView model = new ModelAndView();
		System.out.println("login_on");
		if (error != null) {
			System.out.println(error);
			System.out.println("login_error");
			model.addObject("error", "Invalid or inactive data!");
		}
 
		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");
 
		return model;
	}
	
	@RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
	public ModelAndView accessDenied() 
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("accessDenied");
		return model; 
	}
	
	@RequestMapping(value = "/success", method = RequestMethod.GET)
	public String success() 
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    
	    long idLong = Long.parseLong(name);
	    if(crewMemberService.getCrewMemberByCrewMemberId(idLong) != null) {
	    	return "redirect:/userCrewMember.html";
	    }else if(planningMemberService.getPlanningMemberByPlanningMemberId(idLong) != null ){
	    	return "redirect:/userPlanningMember.html";
	    }else if(idLong==123456) {
	    	return "redirect:/hello.html";
	    }
	    return "redirect:/accessDenied.html";
		
	}
}