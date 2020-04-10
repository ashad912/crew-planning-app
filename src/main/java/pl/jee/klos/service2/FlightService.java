package pl.jee.klos.service2;

import java.util.Date;
import java.util.List;

import pl.jee.klos.domain2.CrewMember;
import pl.jee.klos.domain2.Flight;
import pl.jee.klos.domain2.Plane;

public interface FlightService {
	
	//GET
	public Flight getFlight(int id);
	public Flight getFlightByFlightId(long flightId);
	public List<Flight> getFlightByPlane (Plane plane);
	public List<Flight> getFlightsByCrewMember (CrewMember crewMember);
	public List<Flight> getAllFlights();
//	public Date getFlightStartDate(Flight flight);
//	public Date getFlightEndDate(Flight flight);
	
	
	//POST, DELETE
	public void addFlight(Flight flight);
	public void editFlight(Flight flight);
	public void deleteFlight(Flight flight);

}
