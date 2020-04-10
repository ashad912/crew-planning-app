package pl.jee.klos.dao2;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.jee.klos.domain2.CrewMember;
import pl.jee.klos.domain2.Flight;
import pl.jee.klos.domain2.Plane;
import pl.jee.klos.domain2.PlanningMember;

@Transactional
@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer>{
	
	Flight findById(int id);
	Flight findByFlightId(long flightId);
	Flight findByPlanningMember (PlanningMember planningMember);
	List<Flight> findByPlane(Plane plane);
	List<Flight> findByCrewMember(CrewMember crewMember);

}
