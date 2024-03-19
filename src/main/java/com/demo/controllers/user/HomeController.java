package com.demo.controllers.user;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.controllers.vnp.Config;
import com.demo.entities.Movie;
import com.demo.entities.Shows;
import com.demo.enums.MovieStatus;
import com.demo.helpers.DateHelper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import static com.demo.helpers.DateHelper.*;
import com.demo.services.CinemaService;
import com.demo.services.CityService;
import com.demo.services.MovieService;

import jakarta.servlet.http.HttpServletRequest;
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
		@RequestParam(value = "search", required = false) String search,
		@RequestParam(value = "cinemaId", required = false) Integer cinemaId) {
		List<Movie> beingShownMovies = new ArrayList<>();  // dang chieu
		List<Movie> toBeShownMovies = new ArrayList<>();  // sap chieu	
		if(search != null) {
			beingShownMovies = movieService.searchMoviesByTitle(search);
		} else {
			beingShownMovies = movieService.findAll(cinemaId, MovieStatus.BEING_SHOWN);
			toBeShownMovies = movieService.findAll(cinemaId, MovieStatus.ABOUT_TO_BE_SHOWN);		
		}
		
		modelMap.put("beingShownMovies", beingShownMovies);
		modelMap.put("toBeShownMovies", toBeShownMovies);
		modelMap.put("searchedKeyword", search);
		modelMap.put("cinemaId", cinemaId);
		return "home/index";
	}
	
	@GetMapping("details/{id}")
	public String details(ModelMap modelMap, Authentication authentication,
			@PathVariable("id") int id) {
		var isLoggedIn = authentication != null && authentication.isAuthenticated();
        modelMap.addAttribute("isLoggedIn", isLoggedIn);
		modelMap.put("isMovieShowingNow", movieService.isMovieShowingNow(id));
		modelMap.put("movie", movieService.findMovieById(id));
		modelMap.put("cities", cityService.findAll());
		return "home/details";
	}
	
	@GetMapping("choose-cinema")
	public String chooseCinema(ModelMap modelMap,
			@RequestParam(value = "movieId", required = false) Integer movieId,
			@RequestParam("cityId") int cityId) {
		
		modelMap.put("movieId", movieId);
		modelMap.put("cinemas", cinemaService.findCinemasFromCityAndMovie(cityId, movieId));
		return "home/choose-cinema";
	}
	
	@GetMapping("choose-movie")
	public String chooseMovie(ModelMap modelMap,
			@RequestParam("cinemaId") Integer cinemaId) {
		
		modelMap.put("cinemaId", cinemaId);
		modelMap.put("movies", movieService.findAll(cinemaId, MovieStatus.BEING_SHOWN));
		return "home/choose-movie";
	}

	@GetMapping("choose-time")
	public String choosetime(ModelMap modelMap,
			@RequestParam("movieId") Integer movieId,
			@RequestParam("cinemaId") Integer cinemaId,
			@DateTimeFormat(pattern = DEFAULT_DATE_FORMAT) @RequestParam(value = "date", required = false) Date date) {
		List<Date> datesFromNowToLastShow = movieService.findDatesFromCinemaAndMovieUntilNoutFoundFromNow(cinemaId, movieId);
			
		date = date == null ? datesFromNowToLastShow.get(0) : date;
		List<Shows> shows = movieService.findShowsFromCinemaAndMovie(cinemaId, movieId, date);
		
		modelMap.put("dates", datesFromNowToLastShow);
		modelMap.put("shows", shows);
		modelMap.put("movieId", movieId);
		modelMap.put("cinema", cinemaService.findById(cinemaId));
		modelMap.put("selectedDate", formatDate(date, DEFAULT_DATE_FORMAT));
		return "home/choose-time";
	}
	
	
}
