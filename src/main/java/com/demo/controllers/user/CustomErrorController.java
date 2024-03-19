package com.demo.controllers.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.entities.Movie;
import com.demo.entities.Shows;
import com.demo.enums.MovieStatus;
import com.demo.helpers.DateHelper;

import static com.demo.helpers.DateHelper.*;
import com.demo.services.CinemaService;
import com.demo.services.CityService;
import com.demo.services.MovieService;

@Controller
public class CustomErrorController implements ErrorController {

	@RequestMapping("/error")
	public String handleError() {
		// Handle the 404 error here
		return "error/404"; // Assuming you have a custom error page named 404.html in a folder named
							// "error"
	}

}
