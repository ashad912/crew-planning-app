package pl.jee.klos.dao2;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.jee.klos.domain2.CrewMember;
import pl.jee.klos.domain2.Flight;
import pl.jee.klos.domain2.Plane;

@Transactional
@Repository
public interface PlaneRepository extends JpaRepository<Plane, Integer> {
	Plane findById(int id);
	Plane findByPlaneId(long planeId);
	Plane findByPlaneName (String planeName);
}
