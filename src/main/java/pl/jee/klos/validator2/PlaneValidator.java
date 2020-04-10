package pl.jee.klos.validator2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import pl.jee.klos.domain2.CrewMember;
import pl.jee.klos.domain2.Plane;
import pl.jee.klos.domain2.PlanningMember;
import pl.jee.klos.service2.CrewMemberService;
import pl.jee.klos.service2.PlaneService;
import pl.jee.klos.service2.PlanningMemberService;

public class PlaneValidator implements Validator{
	
	@Override
	public boolean supports(Class clazz) {
		return Plane.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
	}
	
	public void validate(Plane plane, Errors errors, PlaneService planeService) {
		if(plane.getPlaneId().intValue() <= 0) {
			errors.rejectValue("planeId", "error.only.digits");
		}
		ValidationUtils.rejectIfEmpty(errors, "planeId", "error.field.required");
		ValidationUtils.rejectIfEmpty(errors, "planeName", "error.field.required");
		if(plane.getPlaneId()!=null) {
			if(!validateId(plane, planeService))
				errors.rejectValue("planeId", "error.existing.id");
		}
	}
	
	
	private boolean validateId(Plane plane, PlaneService planeService) {
		List<Plane> allPlanes = planeService.getAllPlanes();
		for(Plane takenPlane : allPlanes) {
			if(plane.getId()!=takenPlane.getId()) {
				if(plane.getPlaneId().equals(takenPlane.getPlaneId()))
					return false;
			}
		}
		return true;
	}

}
