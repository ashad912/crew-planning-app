package pl.jee.klos.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import pl.jee.klos.service2.FlightService;
import pl.jee.klos.service2.PersonService;

public class DateConverter implements Converter<String, Date> {

	@Autowired
	PersonService personService;
	
	@Autowired
	FlightService flightService;
	
	@Override
	public Date convert(String source) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(source);
        } catch (ParseException e) {
            return null;
        }
	}

}
