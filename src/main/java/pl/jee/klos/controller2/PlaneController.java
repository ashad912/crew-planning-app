package pl.jee.klos.controller2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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

import pl.jee.klos.domain2.CrewMember;
import pl.jee.klos.domain2.Flight;
import pl.jee.klos.domain2.Plane;
import pl.jee.klos.service2.CrewMemberService;
import pl.jee.klos.service2.FlightService;
import pl.jee.klos.service2.PlaneService;
import pl.jee.klos.validator2.PlaneValidator;

@Controller
@SessionAttributes
public class PlaneController {
	
	@Autowired
	PlaneService planeService;
	
	@Autowired
	FlightService flightService;
	
	PlaneValidator planeValidator = new PlaneValidator();
	
	@RequestMapping(value = "/planes",  method = RequestMethod.GET)
	public String listPlanes(Map<String, Object> map, HttpServletRequest request) {
		
		int planeId = ServletRequestUtils.getIntParameter(request, "planeId", -1);
		
		if (planeId > 0)
			map.put("plane", planeService.getPlane(planeId));
		else
			map.put("plane", new Plane());

		map.put("planeList", planeService.getAllPlanes());

	    return "plane";
	}
	
	@RequestMapping(value = "/addPlane", method = RequestMethod.POST)
	public String addPlane(@ModelAttribute("plane")
    Plane plane, BindingResult result,  HttpServletRequest request, Map<String, Object> map) throws Exception {
		planeValidator.validate(plane, result, planeService);
		
		if(result.getErrorCount()==0) {
			if (plane.getId()==0) {
				planeService.addPlane(plane);
			}else {
				planeService.editPlane(plane);
			}
		}	
		
		map.put("planeList", planeService.getAllPlanes());
		
		return "plane";
		
		
		
	}
	@RequestMapping("/delete/plane/{planeId}")
    public String deletePlane(@PathVariable("planeId") Integer planeId) {
        
        
       Plane plane = planeService.getPlane(planeId);
       List<Flight> flights = flightService.getFlightByPlane(plane);
 	   for (Flight takenFlight: flights) {
 		   flightService.deleteFlight(takenFlight);
 	   }
 	   
 	  planeService.deletePlane(planeService.getPlane(planeId));

        return "redirect:/planes.html";
	}
	
	@RequestMapping(value = "/planeDetails", method = RequestMethod.GET)
    public String planeDetails(Map<String, Object> map, HttpServletRequest request) {
		
		int chosenMonth = ServletRequestUtils.getIntParameter(request, "chosenMonth", -1);
		int planeId = ServletRequestUtils.getIntParameter(request, "planeId", -1);
		System.out.println("PlaneID: " + planeId +" ChosenMonth: " + chosenMonth);
		map.put("plane", planeService.getPlane(planeId));
		
		List<Flight> allFlights = planeService.getPlane(planeId).getFlightList();
		List<Flight> monthFlights = new ArrayList<Flight>();
		
		if(chosenMonth>0) {
			for(Flight flight : allFlights) {
				if(assignFlightsToMonths(chosenMonth, flight))
					monthFlights.add(flight);
			}
			map.put("flightsForPlane", monthFlights);
			
		}else {
			map.put("flightsForPlane", allFlights);
		}
		

        return "planeDetails";
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
