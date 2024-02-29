package com.demo.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.services.CityService;
import com.demo.services.MovieService;

@Controller
@RequestMapping({ "home", "" })
public class HomeController {
	@Autowired
	private MovieService movieService;
	@Autowired
	private CityService cityService;

	@RequestMapping(value = { "", "index", "/" }, method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		modelMap.put("movies", movieService.findAllMovie());
		return "home/index";
	}

	@GetMapping(value = "details/{id}")
	public String details(ModelMap modelMap, @PathVariable("id") int id) {
		modelMap.put("movies", movieService.findMovieById(id));
		return "home/details";
	}

	@RequestMapping(value = { "interest/{id}" }, method = RequestMethod.GET)
	public String interest(ModelMap modelMap, @PathVariable("id") int id) {
		modelMap.put("cities", cityService.findAll());
		modelMap.put("movies", movieService.findMovieById(id));
		return "home/interest";
	}
 
}
