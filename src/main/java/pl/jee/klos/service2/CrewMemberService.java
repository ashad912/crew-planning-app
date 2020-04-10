package pl.jee.klos.service2;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

import pl.jee.klos.domain2.CrewMember;
import pl.jee.klos.domain2.Flight;
import pl.jee.klos.domain2.PersonRole;

public interface CrewMemberService {
	
	//GET
	public CrewMember getCrewMember(int id);
	public CrewMember getCrewMemberByCrewMemberId(long crewMemberId);
	public CrewMember getCrewMemberByLastname(String lastname);
	public List<CrewMember> getCrewMembersByFlight (Flight flight);
	public List<CrewMember> getAllCrewMembers();
	
	//POST, DELETE
	public void addCrewMember(CrewMember crewMember);
	public void editCrewMember(CrewMember crewMember);
	public void deleteCrewMember(CrewMember crewMember);
	

	public String hashPassword(String password);
	

}
