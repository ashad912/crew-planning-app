package pl.jee.klos.utils;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import pl.jee.klos.domain2.PersonRole;
import pl.jee.klos.service2.PersonService;

public class PersonRoleListConverter implements Converter<String[], Set<PersonRole>> {

	@Autowired
	PersonService personService;
	
	@Override
	public Set<PersonRole> convert(String[] source) {
		
		Set<PersonRole> personRoleList = new HashSet<PersonRole>(0);
		
		for (int i=0; i < source.length; i++)
		{
			//System.out.println("role id: " + source[i]);
			personRoleList.add(personService.getPersonRole(Integer.parseInt(source[i])));
		}
		
		return personRoleList;
	}

}
