package pl.jee.klos.service2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.jee.klos.domain2.PersonRole;
//@Service
//@Transactional
public interface PersonService {


	
	
	
	public String hashPassword(String password);
	
	public void addPersonRole(PersonRole personRole);
	public List<PersonRole> listPersonRole();
	public void deletePersonRole (PersonRole personRole);
	public PersonRole getPersonRole(int id);
	public PersonRole getPersonRoleByName (String name);



}
