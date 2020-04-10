package pl.jee.klos.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.MultipartFile;

import pl.jee.klos.service2.CrewMemberService;
import pl.jee.klos.service2.PersonService;
import pl.jee.klos.service2.PlanningMemberService;

public class MultipartFileConverter implements Converter<MultipartFile, byte[]>{

	@Autowired
	PersonService personService;
	
	@Autowired
	CrewMemberService crewMemberService;
	
	
	
	
	@Override
	public byte[] convert(MultipartFile source) {
		try{
			if (!source.isEmpty())
		return source.getBytes();
		}
		catch(Exception e){}
		return null;
	}
}

