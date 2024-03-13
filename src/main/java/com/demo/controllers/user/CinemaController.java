package com.demo.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.MovieStatus;
import com.demo.services.CinemaService;
import com.demo.services.CityService;
import com.demo.services.MovieService;

@Controller
@RequestMapping({ "cinema" })
public class CinemaController {
	
	private CityService cityService;
	private CinemaService cinemaService;
	private MovieService movieService;
	
	@Autowired
	public CinemaController(CityService cityService, CinemaService cinemaService, MovieService movieService) {
		super();
		this.cityService = cityService;
		this.cinemaService = cinemaService;
		this.movieService = movieService;
	}

	@GetMapping("choose-city")
	public String city(ModelMap modelMap) {
		modelMap.put("cities", cityService.findAll()); 
		return "cinema/choose-city";
	}

	@GetMapping("choose-cinema")
	public String select(ModelMap modelMap, @RequestParam("cityId") Integer cityId) {
		modelMap.put("cinemas", cinemaService.findCinemaByCityId(cityId));
		return "cinema/choose-cinema";
	}
	
	@GetMapping("choose-movie")
	public String chooseMovie(ModelMap modelMap, @RequestParam("cinemaId") Integer cinemaId) {
		modelMap.put("movies", movieService.findAll(cinemaId, MovieStatus.BEING_SHOWN));
		modelMap.put("cinemaId", cinemaId);
		return "cinema/choose-movie";
	}
	

}
