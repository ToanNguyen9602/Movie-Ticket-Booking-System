package com.demo.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.services.CityService;

@Controller
@RequestMapping({ "cinema" })
public class CinemaController {
	
	@Autowired
	private CityService cityService;

	@GetMapping(value = { "index" })
	public String city(ModelMap modelMap) {
		modelMap.put("cities", cityService.findAll()); 
		return "cinema/index";
	}

	@GetMapping(value = "details/{id}")
	public String details(ModelMap modelMap, @PathVariable("id") int id) {
		modelMap.put("cinemas", cityService.findCinemasByCityId(id));
		return "cinema/details";
	}

}
