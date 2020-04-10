package pl.jee.klos.utils;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;


import pl.jee.klos.service2.CrewMemberService;
import pl.jee.klos.service2.FlightService;
import pl.jee.klos.service2.PlaneService;
import pl.jee.klos.service2.PlanningMemberService;

public class LongConverter implements Converter<String, Long>{

	@Autowired
	PlaneService planeService;
	
	@Autowired
	FlightService flightService;
	
	@Autowired
	CrewMemberService crewMemberService;
	
	@Autowired
	PlanningMemberService planningMemberService;
	
	
	@Override
	public Long convert(String source){
		if (source.isEmpty())
			return null;
		
		try {
			return Long.parseLong(source);
		}catch (NumberFormatException e) {
			return (long) 0;
		}
	}
}


