package pl.jee.klos.dao2;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.jee.klos.domain2.CrewMember;
import pl.jee.klos.domain2.Flight;

@Transactional
@Repository
public interface CrewMemberRepository extends JpaRepository<CrewMember, Integer>{
	CrewMember findById(int id);
	CrewMember findByCrewMemberId(long crewMemeberId);
	CrewMember findByLastname (String lastname);
	List<CrewMember> findByFlight(Flight flight);

}
