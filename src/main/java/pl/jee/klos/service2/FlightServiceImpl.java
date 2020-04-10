package pl.jee.klos.service2;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.jee.klos.dao2.FlightRepository;
import pl.jee.klos.dao2.PlanningMemberRepository;
import pl.jee.klos.domain2.CrewMember;
import pl.jee.klos.domain2.Flight;
import pl.jee.klos.domain2.Plane;
import pl.jee.klos.domain2.PlanningMember;

@Service
@Transactional
public class FlightServiceImpl implements FlightService {
	
	@Autowired
	FlightRepository repository;
	
	@Autowired
	PlanningMemberRepository planningMemberRepository;
	
	@Override
	public Flight getFlight(int id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}
	
	@Override
	public Flight getFlightByFlightId(long flightId) {
		// TODO Auto-generated method stub
		return repository.findByFlightId(flightId);
	}
	
	@Override
	public List<Flight> getFlightByPlane(Plane plane) {
		// TODO Auto-generated method stub
		System.out.println("Listed flights by Plane: " + plane.getPlaneId());
		return repository.findByPlane(plane);
	}
	
	@Override
	public List<Flight> getFlightsByCrewMember(CrewMember crewMember) {
		// TODO Auto-generated method stub
		
		System.out.println("Listed flights by CrewMember: " + crewMember.getCrewMemberId());
		return repository.findByCrewMember(crewMember);
	}
	@Override
	public List<Flight> getAllFlights() {
		// TODO Auto-generated method stub
		
		System.out.println("Listed all flights");
		return repository.findAll();
	}

	

	@Override
	public void addFlight(Flight flight) {
		// TODO Auto-generated method stub
		System.out.println("Added - FlightId: " + flight.getFlightId() +
                " StartDate: " + flight.getStartDate().toString() + " EndDate: "
				+ flight.getEndDate().toString() );
		repository.save(flight);
	}
	@Override
	public void editFlight(Flight flight) {
		// TODO Auto-generated method stub
		System.out.println("Edited - FlightId: " + flight.getFlightId() +
                " StartDate: " + flight.getStartDate().toString() + " EndDate: "
				+ flight.getEndDate().toString() );
		repository.save(flight);
	}
	@Override
	public void deleteFlight(Flight flight) {
		// TODO Auto-generated method stub
		PlanningMember pM = flight.getPlanningMember();
		
		if(pM!=null) {
			pM.setFlight(null);
			planningMemberRepository.save(pM);
		}
		
		flight.setPlanningMember(null);
		repository.save(flight);
		
		System.out.println("Removed - FlightId: " + flight.getFlightId() +
                " StartDate: " + flight.getStartDate().toString() + " EndDate: "
				+ flight.getEndDate().toString() );
		repository.delete(flight);
	}

	


}
