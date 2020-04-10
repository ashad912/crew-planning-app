package pl.jee.klos.controller2;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import pl.jee.klos.domain2.PersonRole;

import pl.jee.klos.service2.PersonService;


@Controller
@SessionAttributes
public class PersonController {
	
	@Autowired
	PersonService personService;
	

  
   @RequestMapping("/personRole")
	public ModelAndView showPersonRole() {

		return new ModelAndView("personRole", "personRole", new PersonRole());
	}

	@RequestMapping(value = "/addPersonRole", method = RequestMethod.POST)
	public String addPersonRole(@ModelAttribute("personRole") PersonRole personRole, BindingResult result,
			HttpServletRequest request, Map<String, Object> map) {

		personService.addPersonRole(personRole);

		return "redirect:persons.html";
	}
	
}
