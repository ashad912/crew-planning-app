package pl.jee.klos.controller2;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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

import pl.jee.klos.service2.CrewMemberService;
import pl.jee.klos.service2.FlightService;
import pl.jee.klos.service2.PdfView;
import pl.jee.klos.service2.PersonService;

import pl.jee.klos.service2.PlanningMemberService;
import pl.jee.klos.validator2.CrewMemberValidator;


@Controller
@SessionAttributes
public class CrewMemberController {
	
	@Autowired
	CrewMemberService crewMemberService;
	
	@Autowired
	PlanningMemberService planningMemberService;
	
	@Autowired
	PersonService roleService;
	
	@Autowired
	FlightService flightService;
	
	
	CrewMemberValidator crewMemberValidator = new CrewMemberValidator();
	
	
	@RequestMapping("/crewMembers")
	public String listCrewMembers(Map<String, Object> map, HttpServletRequest request) {
	   
		int crewMemberId = ServletRequestUtils.getIntParameter(request, "crewMemberId" , -1);
		   
		if (crewMemberId > 0) {
			CrewMember crewMember = crewMemberService.getCrewMember(crewMemberId);
			crewMember.setPassword("");
			map.put("crewMember", crewMember);
		}else
	    	map.put("crewMember", new CrewMember());   
	       
		map.put("crewMemberList", crewMemberService.getAllCrewMembers());

	    return "crewMember";
	}
	
   @RequestMapping(value = "/addCrewMember", method = RequestMethod.POST)
   public String addCrewMember(@ModelAttribute("crewMember")
                           CrewMember crewMember, BindingResult result, HttpServletRequest request, Map<String, Object> map,
                           @RequestParam("avatar") MultipartFile file) throws Exception {
        
	   crewMemberValidator.validate(crewMember, result, crewMemberService, planningMemberService);
	   
	   if (result.getErrorCount() == 0) {
		   
		   Set<PersonRole> crewMemberRoles = new HashSet<PersonRole>();
		   crewMemberRoles.add(roleService.getPersonRoleByName("ROLE_USER_CM"));
		   
		   crewMember.setPersonRole(crewMemberRoles);
		   
		   if (crewMember.getId()==0) {
			   
			   if(file.getBytes()!=null)
				   crewMember.setAvatar((file.getBytes()));
			   crewMemberService.addCrewMember(crewMember);
		   }else {
			   if(file.getBytes()!=null)
				   crewMember.setAvatar((file.getBytes()));
			   crewMemberService.editCrewMember(crewMember);
		   }
	       System.out.println("First Name: " + crewMember.getFirstname() +
	                   " Last Name: " + crewMember.getLastname());
	   
       return "redirect:crewMembers.html";
	   }
	   map.put("crewMemberList", crewMemberService.getAllCrewMembers());
	   
	   return "crewMember";
   }
   
   
   
   @RequestMapping("/delete/crewMember/{crewMemberId}")
   public String deleteCrewMember(@PathVariable("crewMemberId") Integer crewMemberId) {
	   
       crewMemberService.deleteCrewMember(crewMemberService.getCrewMember(crewMemberId));

       return "redirect:/crewMembers.html";
   }
   
   @RequestMapping(value = "/crewMembers/picture.html")
   public void pobierzPicture(HttpServletRequest request, HttpServletResponse response) {
	   
	   int crewMemberId = ServletRequestUtils.getIntParameter(request, "crewMemberId" , -1);

	   if(crewMemberId<1) return;
	   
	   CrewMember crewMember = crewMemberService.getCrewMember(crewMemberId);
	   if(crewMember.getAvatar() == null) 
	   {
		   return;
	   }
	   try {
		   OutputStream o = response.getOutputStream();
		   response.setCharacterEncoding("UTF-8");
		   response.setContentType("image/jpg");
		   response.setHeader("Content-Disposition", "inline; filename=" + crewMember.getCrewMemberId() + "_picture.jpg");
		   o.write(crewMember.getAvatar());
		   o.flush();
		   o.close();
		   
		   } catch (IOException e) { }
		   
	   
   }
   @RequestMapping(value = "/crewMemberDetails", method = RequestMethod.GET)
   public String crewMemberDetails(Map<String, Object> map, HttpServletRequest request) {
		
		int chosenMonth = ServletRequestUtils.getIntParameter(request, "chosenMonth", -1);
		int crewMemberId = ServletRequestUtils.getIntParameter(request, "crewMemberId", -1);
		System.out.println("CrewMemberID: " + crewMemberId +" ChosenMonth: " + chosenMonth);
		map.put("crewMember", crewMemberService.getCrewMember(crewMemberId));
		
		Set<Flight> allFlights = crewMemberService.getCrewMember(crewMemberId).getFlight();
		Set<Flight> monthFlights = new HashSet<Flight>();
		
		if(chosenMonth>0) {
			for(Flight flight : allFlights) {
				if(assignFlightsToMonths(chosenMonth, flight))
					monthFlights.add(flight);
			}
			map.put("flightsForCrewMember", monthFlights);
			
		}else {
			map.put("flightsForCrewMember", allFlights);
		}
		

       return "crewMemberDetails";
	}
   
   @RequestMapping(value = "/userCrewMember", method = RequestMethod.GET)
   public String userCrewMember(Map<String, Object> map, HttpServletRequest request) {
		
		Integer chosenMonth = ServletRequestUtils.getIntParameter(request, "chosenMonth", -1);
		
		map.put("chosenMonth", chosenMonth);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String login = auth.getName();
		long crewMemberIdLong = Long.parseLong(login);
		int crewMemberId = crewMemberService.getCrewMemberByCrewMemberId(crewMemberIdLong).getId();
		
		System.out.println("CrewMemberID: " + crewMemberId +" ChosenMonth: " + chosenMonth);
		map.put("crewMember", crewMemberService.getCrewMember(crewMemberId));
		
		Set<Flight> allFlights = crewMemberService.getCrewMember(crewMemberId).getFlight();
		Set<Flight> monthFlights = new HashSet<Flight>();
		
		if(chosenMonth>0) {
			for(Flight flight : allFlights) {
				if(assignFlightsToMonths(chosenMonth, flight))
					monthFlights.add(flight);
			}
			map.put("flightsForCrewMember", monthFlights);
			
		}else {
			map.put("flightsForCrewMember", allFlights);
		}
		

       return "userCrewMember";
	}
   
   @RequestMapping(value = "/flightPDF.pdf", method = RequestMethod.GET)
   public String getDocuments(Model model, HttpServletRequest request) {
	   
	   Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	   String login = auth.getName();
	   long crewMemberIdLong = Long.parseLong(login);
	   int crewMemberId = crewMemberService.getCrewMemberByCrewMemberId(crewMemberIdLong).getId();
	   
	   int chosenMonth = ServletRequestUtils.getIntParameter(request, "chosenMonth", -1);
	   
	   Set<Flight> allFlights = crewMemberService.getCrewMember(crewMemberId).getFlight();
	   Set<Flight> monthFlights = new HashSet<Flight>();
	   
	   if(chosenMonth>0) {
			for(Flight flight : allFlights) {
				if(assignFlightsToMonths(chosenMonth, flight))
					monthFlights.add(flight);
			}
			model.addAttribute("flights", monthFlights);
			
	   }else {
			model.addAttribute("flights", allFlights);
	   }
	   model.addAttribute("crewMember", crewMemberService.getCrewMemberByCrewMemberId(crewMemberIdLong));
       return "flightPDF.pdf";

   }
   
   @RequestMapping(value = "/crewMemberEditInit", method = RequestMethod.GET)
   public String crewMemberEditByOwn(Map<String, Object> map, HttpServletRequest request) {
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String login = auth.getName();
		long crewMemberIdLong = Long.parseLong(login);
		//int crewMemberId = crewMemberService.getCrewMemberByCrewMemberId(crewMemberIdLong).getId();
		CrewMember editedCrewMember = crewMemberService.getCrewMemberByCrewMemberId(crewMemberIdLong);
		editedCrewMember.setPassword("");
		map.put("crewMemberEdited", editedCrewMember);
		
		

       return "crewMemberEditInit";
	}
   
   @RequestMapping(value = "/crewMemberEdit", method = RequestMethod.POST)
	public String crewMemberEditByCrewMember(@ModelAttribute("crewMemberEdited")
    CrewMember crewMember, BindingResult result, HttpServletRequest request, Map<String, Object> map,
    @RequestParam("avatar") MultipartFile file) throws Exception {
				
	   crewMemberValidator.validate(crewMember, result, crewMemberService, planningMemberService);
	   
	   if (result.getErrorCount() == 0) {
		   
		   Set<PersonRole> crewMemberRoles = new HashSet<PersonRole>();
		   crewMemberRoles.add(roleService.getPersonRoleByName("ROLE_USER_CM"));
		   
		   crewMember.setPersonRole(crewMemberRoles);
		   
		   if (crewMember.getId()==0) {
			   
			   if(file.getBytes()!=null)
				   crewMember.setAvatar((file.getBytes()));
			   crewMemberService.addCrewMember(crewMember);
		   }else {
			   if(file.getBytes()!=null)
				   crewMember.setAvatar((file.getBytes()));
			   crewMemberService.editCrewMember(crewMember);
		   }
	       System.out.println("First Name: " + crewMember.getFirstname() +
	                   " Last Name: " + crewMember.getLastname());
	       return "redirect:/crewMemberEditInit.html";
	   }
	   return "crewMemberEditInit";
       
		
		
	}
   
   
   private boolean assignFlightsToMonths(int chosenMonth, Flight flight) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(flight.getStartDate());
		int startMonth = cal.get(Calendar.MONTH)+1;
		cal.setTime(flight.getEndDate());
		int endMonth = cal.get(Calendar.MONTH)+1;
		System.out.println(startMonth);
		System.out.println(endMonth);
		if(startMonth<=chosenMonth && endMonth>=chosenMonth)
			return true;
		return false;
	}

	
}
