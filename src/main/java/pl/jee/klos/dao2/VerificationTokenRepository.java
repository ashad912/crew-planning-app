package pl.jee.klos.dao2;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.jee.klos.domain2.PlanningMember;
import pl.jee.klos.domain2.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{
	VerificationToken findByToken(String token);
	 
    VerificationToken findByPlanningMember (PlanningMember planningMember);
}
