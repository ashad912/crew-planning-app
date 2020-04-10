package pl.jee.klos.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;


import pl.jee.klos.domain2.Flight;

import pl.jee.klos.service2.FlightService;
import pl.jee.klos.service2.PersonService;
import pl.jee.klos.service2.FlightService;

public class FlightConverter implements Converter<String, Flight> {

	@Autowired
	FlightService flightService;
	
	@Override
	public Flight convert(String source) {
		
		try {
			return flightService.getFlight(Integer.parseInt(source));
		}catch (NumberFormatException e) {
			return null;
		}
	}

}