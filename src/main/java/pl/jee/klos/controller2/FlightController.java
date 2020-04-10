package pl.jee.klos.controller2;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import pl.jee.klos.domain2.CrewMember;
import pl.jee.klos.domain2.Flight;
import pl.jee.klos.domain2.Plane;
import pl.jee.klos.service2.CrewMemberService;
import pl.jee.klos.service2.FlightService;
import pl.jee.klos.service2.PlaneService;
import pl.jee.klos.service2.PlanningMemberService;
import pl.jee.klos.validator2.FlightValidator;


@Controller
@SessionAttributes
public class FlightController {
	
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	FlightService flightService;
	
	@Autowired
	PlaneService planeService;
	
	@Autowired
	CrewMemberService crewMemberService;
	
	@Autowired
	PlanningMemberService planningMemberService;
	
	FlightValidator flightValidator = new FlightValidator();
	
	@RequestMapping(value = "/flights",  method = RequestMethod.GET)
	public String listFlights(Map<String, Object> map, HttpServletRequest request) throws ParseException{
		
		int flightId = ServletRequestUtils.getIntParameter(request, "flightId", -1);
		
		if (flightId > 0) {
			Flight flight = flightService.getFlight(flightId);
			flight.setStartDate(DATE_FORMAT.parse(DATE_FORMAT.format(flight.getStartDate())));
			flight.setEndDate(DATE_FORMAT.parse(DATE_FORMAT.format(flight.getEndDate())));
			map.put("flight", flightService.getFlight(flightId));
			map.put("crewMemberListForFlight", flightService.getFlight(flightId).getCrewMember());
		}else {
			map.put("flight", new Flight());
			map.put("crewMemberListForFlight", null);
		}

		int chosenMonth = ServletRequestUtils.getIntParameter(request, "chosenMonth", -1);
		System.out.println("ChosenMonth: " + chosenMonth);
		
		List<Flight> allFlights = flightService.getAllFlights();
		List<Flight> monthFlights = new ArrayList<Flight>();
		
		if(chosenMonth>0) {
			for(Flight takenflight : allFlights) {
				if(assignFlightsToMonths(chosenMonth, takenflight))
					monthFlights.add(takenflight);
			}
			map.put("flightList", monthFlights);
			
		}else {
			map.put("flightList", allFlights);
		}
		
		map.put("planeList", planeService.getAllPlanes());
		map.put("crewMemberList", crewMemberService.getAllCrewMembers());
		
	    return "flight";
	}
	
	@RequestMapping(value = "/addFlight", method = RequestMethod.POST)
	public String addFlight(@ModelAttribute("flight")
    Flight flight, BindingResult result, HttpServletRequest request, Map<String, Object> map) throws Exception {
		
			
		
		flightValidator.validate(flight, result, flightService);
		for(CrewMember crewMember: flight.getCrewMember()) {
			System.out.println("Errors: " + crewMember.getFirstname());
		};
		List<ObjectError> errorList = result.getAllErrors();
		for(ObjectError obj :errorList) {
			System.out.println("ErrorLog: " + obj.getDefaultMessage());
		}
		System.out.println("Errors: " + result.getErrorCount());
		
		int chosenMonth = ServletRequestUtils.getIntParameter(request, "chosenMonth", -1);
		System.out.println("ChosenMonth: " + chosenMonth);
		
		List<Flight> allFlights = flightService.getAllFlights();
		List<Flight> monthFlights = new ArrayList<Flight>();
		
		if(chosenMonth>0) {
			for(Flight takenflight : allFlights) {
				if(assignFlightsToMonths(chosenMonth, takenflight))
					monthFlights.add(takenflight);
			}
			map.put("flightList", monthFlights);
			
		}else {
			map.put("flightList", allFlights);
		}
		
		if(result.getErrorCount()==0) {
			
			
			if (flight.getId()==0) {
				flightService.addFlight(flight);
			}else {
				flightService.editFlight(flight);
			}
			
			
			return "redirect:flights.html";	
		}
		
		map.put("planeList", planeService.getAllPlanes());
		map.put("crewMemberList", crewMemberService.getAllCrewMembers());
		
		
		
		return "flight";
	}
	
	
	@RequestMapping(value = "/flightEditInit",  method = RequestMethod.GET)
	public String flightEditByPlanningMemberInit(Map<String, Object> map, HttpServletRequest request) {
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String login = auth.getName();
		Long planningMemberIdLong = Long.parseLong(login);
		int flightId = planningMemberService.getPlanningMemberByPlanningMemberId(planningMemberIdLong).getFlight().getId();
		
		if (flightId > 0) {
			map.put("flight", flightService.getFlight(flightId));
			map.put("crewMemberListForFlight", flightService.getFlight(flightId).getCrewMember());
		}else {
			map.put("flight", new Flight());
			map.put("crewMemberListForFlight", null);
		}

		
		map.put("planningMember", planningMemberService.getPlanningMemberByPlanningMemberId(planningMemberIdLong));
		map.put("planeList", planeService.getAllPlanes());
		map.put("crewMemberList", crewMemberService.getAllCrewMembers());
		
	    return "flightEditInit";
	}
	
	@RequestMapping(value = "/flightEdit", method = RequestMethod.POST)
	public String flightEditByPlanningMember(@ModelAttribute("flight")
    Flight flight, BindingResult result, HttpServletRequest request, Map<String, Object> map) throws Exception {
				
		flightValidator.validate(flight, result, flightService);
		List<ObjectError> errorList = result.getAllErrors();
		for(ObjectError obj :errorList) {
			System.out.println("ErrorLog: " + obj.getDefaultMessage());
		}
		System.out.println("Errors: " + result.getErrorCount());
		if(result.getErrorCount()==0) {
			
			
			if (flight.getId()==0) {
				flightService.addFlight(flight);
			}else {
				flightService.editFlight(flight);
			}
			
		return "redirect:/userPlanningMember.html";
		}
		
		
		map.put("planeList", planeService.getAllPlanes());
		map.put("crewMemberList", crewMemberService.getAllCrewMembers());
		if(flight.getStartDate()!=null)
			flight.setStartDate(DATE_FORMAT.parse(DATE_FORMAT.format(flight.getStartDate())));
		if(flight.getEndDate()!=null)
			flight.setEndDate(DATE_FORMAT.parse(DATE_FORMAT.format(flight.getEndDate())));
		
		return "flightEditInit";	
		
	}
	
	

	
	@RequestMapping("/delete/flight/{flightId}")
    public String deletePlane(@PathVariable("flightId") Integer flightId) {
		
		System.out.println("in");
        flightService.deleteFlight(flightService.getFlight(flightId));
        System.out.println("out");

        return "redirect:/flights.html";
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
