package com.demo.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.services.MovieService;

@Controller
@RequestMapping({ "home", "" })
public class HomeController {
	@Autowired
	private MovieService movieService;

	@RequestMapping(value = { "", "index", "/" }, method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		 modelMap.put("movies", movieService.findAllMovie()); 
		 int a;
		return "home/index";
	}
	 
	
}
