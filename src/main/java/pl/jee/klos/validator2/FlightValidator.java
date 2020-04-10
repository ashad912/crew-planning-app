package pl.jee.klos.validator2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import pl.jee.klos.domain2.CrewMember;
import pl.jee.klos.domain2.Flight;
import pl.jee.klos.domain2.Plane;
import pl.jee.klos.service2.FlightService;
import pl.jee.klos.service2.PlaneService;

public class FlightValidator implements Validator{
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static String digitsRegex = ".*\\p{Digit}.*";
	
	@Override
	public boolean supports(Class clazz) {
		return Flight.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
	}
	
	public void validate(Flight flight, Errors errors, FlightService flightService) {
		if(flight.getFlightId()!=null) {
			if(flight.getFlightId().intValue() <= 0) {
				errors.rejectValue("flightId", "error.only.digits");
			}
		}
		ValidationUtils.rejectIfEmpty(errors, "flightId", "error.field.required");
		ValidationUtils.rejectIfEmpty(errors, "startDate", "error.field.required");
		ValidationUtils.rejectIfEmpty(errors, "endDate", "error.field.required");
		if(flight.getFlightId()!=null) {
			if(!validateId(flight, flightService))
				errors.rejectValue("flightId", "error.existing.id");
		}
		if(flight.getStartDate()!=null && flight.getEndDate()!=null) {
			if (flight.getEndDate().getTime() <= flight.getStartDate().getTime() ) {
				errors.rejectValue("startDate", "error.date.order");
				errors.rejectValue("endDate", "error.date.order");
			}
			if(flightConditions(flight, flightService) == -1) {
				errors.rejectValue("plane", "error.plane.forbidden");
			}
			else if(flightConditions(flight, flightService) == -2) {
				errors.rejectValue("crewMember", "error.crewMember.forbidden");
			}
			else if(flightConditions(flight, flightService) == -3){
				errors.rejectValue("plane", "error.plane.forbidden");
				errors.rejectValue("crewMember", "error.crewMember.forbidden");
			}
		}
		if(flight.getCrewMember().isEmpty()) {
			errors.rejectValue("crewMember", "error.crew.empty");
		}
		
		
//		if(flight.getStartDate()!=null)
//			flight.setStartDate(DATE_FORMAT.parse(DATE_FORMAT.format(flight.getStartDate())));
//		if(flight.getEndDate()!=null)
//			flight.setEndDate(DATE_FORMAT.parse(DATE_FORMAT.format(flight.getEndDate())));	
		
	}
	
	private boolean validateId(Flight flight, FlightService flightService) {
		List<Flight> allFlights = flightService.getAllFlights();
		for(Flight takenFlight : allFlights) {
			if(flight.getId()!=takenFlight.getId()) { 
				if(flight.getFlightId().equals(takenFlight.getFlightId()))
					return false;
			}
		}
		return true;
	}
	
	private int flightConditions(Flight flight, FlightService flightService) {
		Date startDateNew = flight.getStartDate();
		Date endDateNew = flight.getEndDate();
		List<Flight> allFlights = flightService.getAllFlights();
		List<Flight> theSameDateFlights = new ArrayList<Flight>();
		
		for(Flight takenFlight : allFlights) {
			if(flight.getId()!=takenFlight.getId()) {
				Date startDateExisting = takenFlight.getStartDate();
				Date endDateExisiting = takenFlight.getEndDate();
				
				if(((startDateNew.getTime()>=startDateExisting.getTime()) && (startDateNew.getTime() <= endDateExisiting.getTime()))
						|| ((endDateNew.getTime()>=startDateExisting.getTime()) && (endDateNew.getTime() <= endDateExisiting.getTime()))){
					theSameDateFlights.add(takenFlight);
					System.out.println(takenFlight.getFlightId());
				}
			}
		}
		if(theSameDateFlights!=null) {
			boolean planeForbidden = false;
			boolean crewForbidden = false;
			for(Flight conflictFlight : theSameDateFlights) {
				Plane newPlane = flight.getPlane();
				Plane existingPlane = conflictFlight.getPlane();
				System.out.println(newPlane.getPlaneId());
				System.out.println(existingPlane.getPlaneId() + "--");
				if(newPlane.getPlaneId().equals(existingPlane.getPlaneId())) {
					planeForbidden = true;
				}
				Set<CrewMember> newCrewMembers = flight.getCrewMember();
				Set<CrewMember> existingCrewMembers = conflictFlight.getCrewMember();
				for(CrewMember existing : existingCrewMembers) {
					for(CrewMember newMember : newCrewMembers) {
						System.out.println(newMember.getCrewMemberId());
						System.out.println(existing.getCrewMemberId() + "--");
						if(newMember.getCrewMemberId().equals(existing.getCrewMemberId())) {
							crewForbidden = true;
						}
					}
				}
			}
		if(planeForbidden && !crewForbidden)
			return -1;
		else if(!planeForbidden && crewForbidden)
			return -2;
		else if(planeForbidden && crewForbidden)
			return -3;
		}
		
			
		return 0;
		
	}
	
	

}
