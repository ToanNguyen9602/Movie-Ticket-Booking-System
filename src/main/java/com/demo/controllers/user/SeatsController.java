package com.demo.controllers.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.entities.Movie;
import com.demo.enums.MovieStatus;
import com.demo.services.MovieService;
import com.demo.services.SeatsService;
@Controller
@RequestMapping({ "seats" })
public class SeatsController {

	@Autowired
	private MovieService movieService;
	
	@Autowired
	private SeatsService seatsService;
	
	@GetMapping({ "", "now-showing" })
	public String nowshowing(ModelMap modelMap) {

		modelMap.put("seats", seatsService.findallSeatsbyHallid(0));
		return "film/now-showing";
	}

}
