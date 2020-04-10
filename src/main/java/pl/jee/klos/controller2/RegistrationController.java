package pl.jee.klos.controller2;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import pl.jee.klos.domain2.CrewMember;
import pl.jee.klos.domain2.Flight;

import pl.jee.klos.domain2.PersonRole;
import pl.jee.klos.domain2.PlanningMember;
import pl.jee.klos.domain2.VerificationToken;

import pl.jee.klos.service2.CrewMemberService;
import pl.jee.klos.service2.FlightService;
import pl.jee.klos.service2.PersonService;

import pl.jee.klos.service2.PlanningMemberService;
import pl.jee.klos.service2.ReCaptchaService;
import pl.jee.klos.validator2.CrewMemberValidator;

import pl.jee.klos.validator2.PlanningMemberValidator;

@Controller
@SessionAttributes
public class RegistrationController {

	@Autowired
	PlanningMemberService planningMemberService;

	@RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
	public String confirmRegistration
	  (WebRequest request, Model model, @RequestParam("token") String token) {
	  
	     
	    VerificationToken verificationToken = planningMemberService.getVerificationToken(token);
	    if (verificationToken == null) {
	    	
	    }
	     
	    PlanningMember planningMember = verificationToken.getPlanningMember();
	    Calendar cal = Calendar.getInstance();
	    if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	    	
	    } 
	     
	    planningMember.setEnabled(true); 
	    planningMemberService.editPlanningMemberWithNoHash(planningMember);
	    return "redirect:/login.html"; 
	}

}
