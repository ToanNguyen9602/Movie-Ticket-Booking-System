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

@Controller
@RequestMapping({ "film" })
public class FilmController {

	@Autowired
	private MovieService movieService;
	
	@GetMapping({ "", "now-showing" })
	public String nowshowing(ModelMap modelMap, Authentication authentication) {
		var isLoggedIn = authentication != null && authentication.isAuthenticated();
        modelMap.addAttribute("isLoggedIn", isLoggedIn);
		modelMap.put("movies", movieService.findAll(null, MovieStatus.BEING_SHOWN));
		return "film/now-showing";
	}

	@GetMapping("coming-soon")
	public String index(ModelMap modelMap) {
		modelMap.put("movies", movieService.findAll(null, MovieStatus.ABOUT_TO_BE_SHOWN));
		return "film/coming-soon";
	}

}
