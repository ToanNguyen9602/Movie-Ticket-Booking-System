package com.demo.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.services.CinemaService;
import com.demo.services.CityService;
import com.demo.services.MovieService;

@Controller
@RequestMapping({ "home", "" })
public class HomeController {
	
	private MovieService movieService;
	private CityService cityService;
	private CinemaService cinemaService;
	
	@Autowired
	public HomeController(MovieService movieService, CityService cityService, CinemaService cinemaService) {
		this.movieService = movieService;
		this.cityService = cityService;
		this.cinemaService = cinemaService;
	}

	@RequestMapping(value = { "", "index", "/" }, method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		modelMap.put("movies", movieService.findAll());
		return "home/index";
	}
	
	@RequestMapping(value = {"index2"}, method = RequestMethod.GET)
	public String index2(ModelMap modelMap) {
		modelMap.put("movies", movieService.findAll());
		return "home/user2";
	}

	@GetMapping(value = "details/{id}")
	public String details(ModelMap modelMap, @PathVariable("id") int id) {
		modelMap.put("movie", movieService.findMovieById(id));
		modelMap.put("cities", cityService.findAll());
		return "home/details";
	}

	@GetMapping(value = "choose-cinema")
	public String chooseCinema(ModelMap modelMap,
			@RequestParam("movieId") int movieId,
			@RequestParam("cityId") int cityId) {
		modelMap.put("cinemas", cinemaService.findCinemasFromCityAndMovie(cityId, movieId));
		return "home/choose-cinema";
	}

	@GetMapping(value = "choose-time")
	public String choosetime(ModelMap modelMap) {
		return "home/choose-time";
	}

}
