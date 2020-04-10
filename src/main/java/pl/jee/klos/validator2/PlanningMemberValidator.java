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


public class PlanningMemberValidator implements Validator{
	
	

	@Override
	public boolean supports(Class clazz) {
		return PlanningMember.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
	}

	public void validate(PlanningMember planningMember, Errors errors, CrewMemberService crewMemberService, PlanningMemberService planningMemberService) {
		
		ValidationUtils.rejectIfEmpty(errors, "planningMemberId", "error.field.required");
		ValidationUtils.rejectIfEmpty(errors, "firstname", "error.field.required");
		ValidationUtils.rejectIfEmpty(errors, "lastname", "error.field.required");
		ValidationUtils.rejectIfEmpty(errors, "password", "error.field.required");
		if(planningMember.getPlanningMemberId()!=null) {
			if(planningMember.getPlanningMemberId().intValue() <= 0) {
				errors.rejectValue("planningMemberId", "error.only.digits");
			}
			if(!validateId(planningMember, crewMemberService, planningMemberService))
				errors.rejectValue("planningMemberId", "error.existing.id");
		}
				
	}
	
	private boolean validateId(PlanningMember planningMember, CrewMemberService crewMemberService, PlanningMemberService planningMemberService) {
		List<Long> allId  = new ArrayList<Long>();
		List<CrewMember> allCrew = crewMemberService.getAllCrewMembers();
		List<PlanningMember> allPlanning = planningMemberService.getAllPlanningMembers();
		for(CrewMember crew : allCrew) {
			allId.add(crew.getCrewMemberId());
		}
		for(PlanningMember planning : allPlanning) {
			if(planningMember.getId()!=planning.getId()) {
				allId.add(planning.getPlanningMemberId());
			}
		}
		
		for(Long takenId : allId) {
			if(planningMember.getPlanningMemberId()==takenId) {
				return false;
			}
		}
		return true;
	}
	

}
