package pl.jee.klos.service2;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pl.jee.klos.dao2.CrewMemberRepository;
import pl.jee.klos.dao2.FlightRepository;

import pl.jee.klos.dao2.PersonRoleRepository;
import pl.jee.klos.dao2.PlanningMemberRepository;
import pl.jee.klos.domain2.CrewMember;
import pl.jee.klos.domain2.Flight;

import pl.jee.klos.domain2.PersonRole;
import pl.jee.klos.domain2.PlanningMember;

@Service
@Transactional
public class CrewMemberServiceImpl implements CrewMemberService {
	
	@Autowired
	CrewMemberRepository repository;
	
	@Autowired
	FlightRepository flightRepository;
	
	@Autowired
	PlanningMemberRepository planningMemberRepository;
	
	@Override
	public CrewMember getCrewMember(int id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}
	
	@Override
	public CrewMember getCrewMemberByCrewMemberId(long crewMemberId) {
		// TODO Auto-generated method stub
		System.out.println("List CrewMember ID: " + crewMemberId);
		return repository.findByCrewMemberId(crewMemberId);
	}
	
	@Override
	public CrewMember getCrewMemberByLastname(String lastname) {
		// TODO Auto-generated method stub
		return repository.findByLastname(lastname);
	}
	
	@Override
	public List<CrewMember> getCrewMembersByFlight(Flight flight) {
		// TODO Auto-generated method stub
		
		System.out.println("Listed crewMembers by Flight: " + flight.getFlightId());
		return repository.findByFlight(flight);
	}
	@Override
	public List<CrewMember> getAllCrewMembers() {
		// TODO Auto-generated method stub
		
		System.out.println("Listed all crewMembers");
		return repository.findAll();
	}

	

	@Override
	public void addCrewMember(CrewMember crewMember) {
		// TODO Auto-generated method stub
		crewMember.setPassword(hashPassword(crewMember.getPassword()));
		System.out.println("Added - First Name: " + crewMember.getFirstname() +
                " Last Name: " + crewMember.getLastname() + " CrewId: " + 
		   crewMember.getCrewMemberId());
		
		repository.save(crewMember);
	}
	@Override
	public void editCrewMember(CrewMember crewMember) {
		// TODO Auto-generated method stub
		crewMember.setPassword(hashPassword(crewMember.getPassword()));
		System.out.println("Edited - First Name: " + crewMember.getFirstname() +
                " Last Name: " + crewMember.getLastname() + " CrewId: " + 
		   crewMember.getCrewMemberId());
		
		repository.save(crewMember);
	}
	@Override
	public void deleteCrewMember(CrewMember crewMember) {
		// TODO Auto-generated method stub
		Set<Flight> flights = crewMember.getFlight();
		if(flights!=null) {
			CrewMember crewMemberToRemove = repository.findById(crewMember.getId());
			for(Flight flight: flights) {
				
				if(planningMemberRepository.findByFlight(flight)!=null) {
					PlanningMember pM = planningMemberRepository.findByFlight(flight);
					pM.setFlight(null);
					planningMemberRepository.save(pM);
				}
				
				flight.getCrewMember().remove(crewMemberToRemove);
				System.out.println("Removed1 - FlightID: " + flight.getId());
				flightRepository.delete(flight);
				System.out.println("Removed2 - FlightID: " + flight.getId());
				
			}
		}
		System.out.println("Removed - First Name: " + crewMember.getFirstname() +
                " Last Name: " + crewMember.getLastname() + " CrewId: " + 
		   crewMember.getCrewMemberId());
		
		crewMember.setFlight(null);
		repository.delete(crewMember);
	}
	@Override
	public String hashPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}


}
