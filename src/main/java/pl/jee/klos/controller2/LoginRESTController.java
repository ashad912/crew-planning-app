package pl.jee.klos.controller2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.jee.klos.dao2.PlanningMemberRepository;

import pl.jee.klos.domain2.PlanningMember;

@RestController
@RequestMapping(value= "loginRest", produces="application/json")
public class LoginRESTController {
 
	@Autowired
	PlanningMemberRepository planningMemberRepository;
	
    @RequestMapping(value = "/{login}", method = RequestMethod.GET, produces = "application/json")
    public PlanningMember getLoginInJSON(@PathVariable Long login) {
    	
   	 return planningMemberRepository.findByPlanningMemberId(login);
    }
 
    @RequestMapping(value = "/{login}.xml", method = RequestMethod.GET, produces = "application/xml")
    public PlanningMember getLoginInXML(@PathVariable Long login) {
 
    return planningMemberRepository.findByPlanningMemberId(login);
    }
 
}