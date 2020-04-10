package pl.jee.klos.validator2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import pl.jee.klos.domain2.CrewMember;
import pl.jee.klos.domain2.PlanningMember;
import pl.jee.klos.service2.CrewMemberService;
import pl.jee.klos.service2.PlanningMemberService;


public class CrewMemberValidator implements Validator{
	

	
	@Override
	public boolean supports(Class clazz) {
		return CrewMember.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
	}

	public void validate(CrewMember crewMember, Errors errors, CrewMemberService crewMemberService, PlanningMemberService planningMemberService) {
		
		ValidationUtils.rejectIfEmpty(errors, "crewMemberId", "error.field.required");
		ValidationUtils.rejectIfEmpty(errors, "firstname", "error.field.required");
		ValidationUtils.rejectIfEmpty(errors, "lastname", "error.field.required");
		ValidationUtils.rejectIfEmpty(errors, "password", "error.field.required");
		
		if(crewMember.getCrewMemberId()!=null) {
			if(crewMember.getCrewMemberId().intValue() <= 0) {
				errors.rejectValue("crewMemberId", "error.only.digits");
			}
			if(!validateId(crewMember, crewMemberService, planningMemberService))
				errors.rejectValue("crewMemberId", "error.existing.id");
		}
	}

	
	private boolean validateId(CrewMember crewMember, CrewMemberService crewMemberService, PlanningMemberService planningMemberService) {
		List<Long> allId  = new ArrayList<Long>();
		List<CrewMember> allCrew = crewMemberService.getAllCrewMembers();
		List<PlanningMember> allPlanning = planningMemberService.getAllPlanningMembers();
		for(CrewMember crew : allCrew) {
			if(crewMember.getId()!=crew.getId()) {
				allId.add(crew.getCrewMemberId());
			}
		}
		for(PlanningMember planning : allPlanning) {
			allId.add(planning.getPlanningMemberId());
		}
		
		for(Long takenId : allId) {
			if(crewMember.getCrewMemberId().equals(takenId)) {
				return false;
			}
		}
		return true;
	}
	

}
