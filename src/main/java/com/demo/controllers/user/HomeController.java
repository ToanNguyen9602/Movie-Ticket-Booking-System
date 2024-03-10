package com.demo.controllers.user;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.entities.Shows;
import com.demo.helpers.DateHelper;

import static com.demo.helpers.DateHelper.*;
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
	@GetMapping({ "", "index", "/" })
	public String index(ModelMap modelMap, 
		@RequestParam(value = "search", required = false) String search) {
		if(search ==null) {
			movieService.findAll().stream().forEach(System.out::println);
			modelMap.put("movies", movieService.findAll());			
		} else {
			modelMap.put("movies", movieService.searchMoviesByTitle(search) );
		}
		modelMap.put("searchedKeyword", search);
		return "home/index";
	}
	
	@GetMapping({"index2"})
	public String index2(ModelMap modelMap) {
		modelMap.put("movies", movieService.findAll());
		return "home/user2";
	}

	@GetMapping("details/{id}")
	public String details(ModelMap modelMap, @PathVariable("id") int id) {
		modelMap.put("movie", movieService.findMovieById(id));
		modelMap.put("cities", cityService.findAll());
		return "home/details";
	}

	@GetMapping("choose-cinema")
	public String chooseCinema(ModelMap modelMap,
			@RequestParam("movieId") int movieId,
			@RequestParam("cityId") int cityId) {
		modelMap.put("movieId", movieId);
		modelMap.put("cinemas", cinemaService.findCinemasFromCityAndMovie(cityId, movieId));
		return "home/choose-cinema";
	}

	@GetMapping("choose-time")
	public String choosetime(ModelMap modelMap,
			@RequestParam("movieId") Integer movieId,
			@RequestParam("cinemaId") Integer cinemaId,
			@DateTimeFormat(pattern = DEFAULT_DATE_FORMAT) @RequestParam(value = "date", required = false) Date date) {
		List<Date> datesFromNowToLastShow = movieService.findDatesFromCinemaAndMovieUntilNoutFoundFromNow(cinemaId, movieId);
			
		date = date == null ? datesFromNowToLastShow.get(0) : date;
		List<Shows> shows = movieService.findShowsFromCinemaAndMovie(cinemaId, movieId, date);
		
		datesFromNowToLastShow.forEach(System.out::println);
		shows.forEach(System.out::println);
		
		modelMap.put("dates", datesFromNowToLastShow);
		modelMap.put("shows", shows);
		modelMap.put("movieId", movieId);
		modelMap.put("cinema", cinemaService.findById(cinemaId));
		modelMap.put("selectedDate", formatDate(date, DEFAULT_DATE_FORMAT));
		return "home/choose-time";
	}
	
	public static Date getNext4Days() {
        LocalDate today = LocalDate.now();
        LocalDate next4Days = today.plusDays(4);
        return java.sql.Date.valueOf(next4Days);
    }
	

}
