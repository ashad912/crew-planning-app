package pl.jee.klos.service2;

import java.util.List;

import pl.jee.klos.domain2.CrewMember;
import pl.jee.klos.domain2.Flight;
import pl.jee.klos.domain2.Plane;

public interface PlaneService {
	//GET
		public Plane getPlane(int id);
		public Plane getPlaneByPlaneId(long planeId);
		public Plane getPlaneByPlaneName (String planeName);
		public List<Plane> getAllPlanes();
		
		
		//POST, DELETE
		public void addPlane(Plane plane);
		public void editPlane(Plane plane);
		public void deletePlane(Plane plane);

}
