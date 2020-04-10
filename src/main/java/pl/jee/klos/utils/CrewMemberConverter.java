package pl.jee.klos.utils;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import pl.jee.klos.domain2.CrewMember;
import pl.jee.klos.domain2.PersonRole;
import pl.jee.klos.service2.CrewMemberService;
import pl.jee.klos.service2.PersonService;

public class CrewMemberConverter implements Converter<String, Set<CrewMember>> {

	@Autowired
	CrewMemberService crewMemberService;
	
	@Override
	public Set<CrewMember> convert(String source) {
		
		Set<CrewMember> crewMemberList = new HashSet<CrewMember>(0);
		
		crewMemberList.add(crewMemberService.getCrewMember(Integer.parseInt(source)));
		
		return crewMemberList;
	}
}
