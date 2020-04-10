package pl.jee.klos.service2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import pl.jee.klos.dao2.PlaneRepository;
import pl.jee.klos.domain2.Plane;

@Service
@Transactional
public class PlaneServiceImpl implements PlaneService{
	
	@Autowired
	PlaneRepository repository;
	
	@Override
	public Plane getPlane(int id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}
	
	@Override
	public Plane getPlaneByPlaneId(long planeId) {
		// TODO Auto-generated method stub
		return repository.findByPlaneId(planeId);
	}
	
	@Override
	public Plane getPlaneByPlaneName(String planeName) {
		// TODO Auto-generated method stub
		
		System.out.println("List Plane by planename: " + planeName);
		return repository.findByPlaneName(planeName);
	}
	@Override
	public List<Plane> getAllPlanes() {
		// TODO Auto-generated method stub
		
		System.out.println("Listed all planes");
		return repository.findAll();
	}

	

	@Override
	public void addPlane(Plane plane) {
		// TODO Auto-generated method stub
		System.out.println("Added - PlaneId: " + plane.getPlaneId() +
                " planeName: " + plane.getPlaneName());
		repository.save(plane);
	}
	@Override
	public void editPlane(Plane plane) {
		// TODO Auto-generated method stub
		System.out.println("Added - PlaneId: " + plane.getPlaneId() +
                " planeName: " + plane.getPlaneName());
		repository.save(plane);
	}
	@Override
	public void deletePlane(Plane plane) {
		// TODO Auto-generated method stub
		
		System.out.println("Added - PlaneId: " + plane.getPlaneId() +
                " planeName: " + plane.getPlaneName());
		repository.delete(plane);
	}


}
