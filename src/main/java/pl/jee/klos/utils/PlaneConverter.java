package pl.jee.klos.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import pl.jee.klos.domain2.Plane;
import pl.jee.klos.service2.FlightService;
import pl.jee.klos.service2.PersonService;
import pl.jee.klos.service2.PlaneService;

public class PlaneConverter implements Converter<String, Plane> {

	@Autowired
	PlaneService planeService;
	
	@Override
	public Plane convert(String source) {
		return planeService.getPlane(Integer.parseInt(source));
	}

}