package pl.jee.klos.service2;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Override;

import pl.jee.klos.dao2.PersonRoleRepository;
import pl.jee.klos.domain2.PersonRole;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

	
	@Autowired
	PersonRoleRepository roleRepository;
	
	

	@Override
	public String hashPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}

	@Override
	public void addPersonRole(PersonRole personRole) {
		// TODO Auto-generated method stub
		roleRepository.save(personRole);
	}

	@Override
	public List<PersonRole> listPersonRole() {
		return roleRepository.findAll();
		
	}

	@Override
	public void deletePersonRole(PersonRole personRole) {
		roleRepository.delete(personRole);
		
	}

	@Override
	public PersonRole getPersonRole(int id) {
		return roleRepository.findById(id);
	}
	
	@Override
	public PersonRole getPersonRoleByName(String name) {
		return roleRepository.findByRole(name);
	}
	
	
	
	
	


}
