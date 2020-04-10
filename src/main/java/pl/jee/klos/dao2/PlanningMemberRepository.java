package pl.jee.klos.dao2;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.jee.klos.domain2.Flight;
import pl.jee.klos.domain2.Plane;
import pl.jee.klos.domain2.PlanningMember;

@Transactional
@Repository
public interface PlanningMemberRepository extends JpaRepository<PlanningMember, Integer>{
	PlanningMember findById(int id);
	PlanningMember findByPlanningMemberId(long planningMemberId);
	PlanningMember findByLastname (String lastname);
	PlanningMember findByFlight (Flight flight);

}
