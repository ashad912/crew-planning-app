package pl.jee.klos.controller2;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import pl.jee.klos.domain2.CrewMember;
import pl.jee.klos.domain2.Flight;

import pl.jee.klos.domain2.PersonRole;
import pl.jee.klos.domain2.PlanningMember;

import pl.jee.klos.service2.CrewMemberService;
import pl.jee.klos.service2.FlightService;
import pl.jee.klos.service2.PersonService;

import pl.jee.klos.service2.PlanningMemberService;
import pl.jee.klos.service2.ReCaptchaService;
import pl.jee.klos.utils.OnRegistrationCompleteEvent;
import pl.jee.klos.validator2.CrewMemberValidator;

import pl.jee.klos.validator2.PlanningMemberValidator;

@Controller
@SessionAttributes
public class PlanningMemberController {
	
	@Autowired
	PlanningMemberService planningMemberService;
	
	@Autowired
	CrewMemberService crewMemberService;
	
	@Autowired
	PersonService roleService;
	
	@Autowired
	FlightService flightService;
	
	@Autowired
	ReCaptchaService reCaptchaService;
	
	@Autowired
	ApplicationEventPublisher eventPublisher;
	
	
	PlanningMemberValidator planningMemberValidator = new PlanningMemberValidator();
	
	
	@RequestMapping("/planningMembers")
	public String listPlanningMembers(Map<String, Object> map, HttpServletRequest request) {
	   
		int planningMemberId = ServletRequestUtils.getIntParameter(request, "planningMemberId" , -1);
		   
		if (planningMemberId > 0) {
			PlanningMember planningMember = planningMemberService.getPlanningMember(planningMemberId);
			planningMember.setPassword("");
			map.put("planningMember", planningMember);
		}else
	    	map.put("planningMember", new PlanningMember());   
	       
		map.put("planningMemberList", planningMemberService.getAllPlanningMembers());
		map.put("flightList", flightService.getAllFlights());
		

	    return "planningMember";
	}
	
   @RequestMapping(value = "/addPlanningMember", method = RequestMethod.POST)
   public String addPlanningMember(@ModelAttribute("planningMember")
                           PlanningMember planningMember, BindingResult result, HttpServletRequest request, Map<String, Object> map, 
                           @RequestParam(name="g-recaptcha-response") String recaptchaResponse) throws Exception {
        
	   planningMemberValidator.validate(planningMember, result, crewMemberService, planningMemberService);
	   if (!reCaptchaService.validateReCaptcha(recaptchaResponse) ) {
		   map.put("error", "Invalid captcha!");
	   } else if (result.getErrorCount() == 0 && isNewConnection(planningMember)) {
		   
		   Set<PersonRole> planningMemberRoles = new HashSet<PersonRole>();
		   planningMemberRoles.add(roleService.getPersonRoleByName("ROLE_USER_PM"));
		   if(planningMember.getAdmin())
			   planningMemberRoles.add(roleService.getPersonRoleByName("ROLE_ADMIN"));
		   
		   planningMember.setPersonRole(planningMemberRoles);
		   
		   if (planningMember.getId()==0) {
			   planningMemberService.addPlanningMember(planningMember);
			   try {
			        String appUrl = request.getContextPath();
			        System.out.println("in");
			        eventPublisher.publishEvent(new OnRegistrationCompleteEvent
			          (planningMember, request.getLocale(), appUrl));
			        System.out.println("out");
			    } catch (Exception me) {
			    	System.out.println("exp");
			    	map.put("emailError", "Bad email!");
			    }
			   
		   }else {
			   planningMember.setEnabled(true);
			   planningMemberService.editPlanningMember(planningMember);
		   }
	       System.out.println("First Name: " + planningMember.getFirstname() +
	                   " Last Name: " + planningMember.getLastname());
	   
       return "redirect:planningMembers.html";
	   }
	   map.put("planningMemberList", planningMemberService.getAllPlanningMembers());
	   map.put("flightList", flightService.getAllFlights());
	   
	   return "planningMember";
   }
   
   
   
   @RequestMapping("/delete/planningMember/{planningMemberId}")
   public String deletePlanningMember(@PathVariable("planningMemberId") Integer planningMemberId) {
	   	
	   if(planningMemberService.getPlanningMember(planningMemberId)!=null) {
		   PlanningMember planningMember = planningMemberService.getPlanningMember(planningMemberId);
		   if(planningMemberService.getVerificationTokenByPlanningMember(planningMember)!=null) {
			   planningMemberService.deleteToken(planningMemberService.getVerificationTokenByPlanningMember(planningMember));
		   }
		   planningMemberService.deletePlanningMember(planningMemberService.getPlanningMember(planningMemberId));
	   }
       return "redirect:/planningMembers.html";
   }
   
   @RequestMapping(value = "/userPlanningMember", method = RequestMethod.GET)
   public String userPlanningMember(Map<String, Object> map, HttpServletRequest request) {
	    
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String login = auth.getName();
		long planningMemberIdLong = Long.parseLong(login);
		
		if(planningMemberService.getPlanningMemberByPlanningMemberId(planningMemberIdLong) !=null) {
			
			int planningMemberId = planningMemberService.getPlanningMemberByPlanningMemberId(planningMemberIdLong).getId();
			
			System.out.println("PlanningMemberID: " + planningMemberId);
			map.put("planningMember", planningMemberService.getPlanningMember(planningMemberId));
			
			if(planningMemberService.getPlanningMember(planningMemberId).getFlight() != null) {
				map.put("planningMemberFlight", planningMemberService.getPlanningMember(planningMemberId).getFlight());
			}else {
				map.put("planningMemberFlight", null);
			}
		}
		
		

       return "userPlanningMember";
	}
   
   @RequestMapping(value = "/planningMemberEditInit", method = RequestMethod.GET)
   public String planningMemberEditByOwn(Map<String, Object> map, HttpServletRequest request) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String login = auth.getName();
		Long planningMemberIdLong = Long.parseLong(login);
		//int planningMemberId = planningMemberService.getPlanningMemberByPlanningMemberId(planningMemberIdLong).getId();
		PlanningMember editedPlanningMember = planningMemberService.getPlanningMemberByPlanningMemberId(planningMemberIdLong);
		editedPlanningMember.setPassword("");
		map.put("planningMember", editedPlanningMember);
		map.put("loginLong", planningMemberIdLong);
		
		

       return "planningMemberEditInit";
	}
   
   @RequestMapping(value = "/planningMemberEdit", method = RequestMethod.POST)
	public String planningMemberEditByPlanningMember(@ModelAttribute("planningMember")
    PlanningMember planningMember, BindingResult result, HttpServletRequest request, Map<String, Object> map) throws Exception {
				
	   planningMemberValidator.validate(planningMember, result, crewMemberService, planningMemberService);
	   
	   if (result.getErrorCount() == 0) {
		   PlanningMember old = planningMemberService.getPlanningMember(planningMember.getId());
		   
		   Set<PersonRole> planningMemberRoles = new HashSet<PersonRole>();
		   planningMemberRoles.add(roleService.getPersonRoleByName("ROLE_USER_PM"));
		   
		   if(old.getAdmin())
			   planningMemberRoles.add(roleService.getPersonRoleByName("ROLE_ADMIN"));
		   
		   planningMember.setPersonRole(planningMemberRoles);
		   
		   if (planningMember.getId()==0) {
			   planningMemberService.addPlanningMember(planningMember);
		   }else {
			   planningMember.setAdmin(old.getAdmin());
			   planningMember.setFlight(old.getFlight());
			   planningMember.setEnabled(old.getEnabled());
			   planningMemberService.editPlanningMember(planningMember);
		   }
	       System.out.println("First Name: " + planningMember.getFirstname() +
	                   " Last Name: " + planningMember.getLastname());
	       return "redirect:/planningMemberEditInit.html";
	   }
	   return "planningMemberEditInit";
       
		
		
	}
   	
   	private boolean isNewConnection(PlanningMember planningMember) {
   		if(planningMember.getFlight()!=null) {
	   		List<PlanningMember> allPM = planningMemberService.getAllPlanningMembers();
	   		for(PlanningMember takenPM : allPM) {
	   			if(takenPM.getFlight()!=null) {
	   				if(planningMember.getId()!=takenPM.getId()) {
	   					if(planningMember.getFlight().getId() == takenPM.getFlight().getId()) {
	   						return false;
	   					}
	   				}
	   			}
	   		}
   		}
   	return true;
   	}
	
}
