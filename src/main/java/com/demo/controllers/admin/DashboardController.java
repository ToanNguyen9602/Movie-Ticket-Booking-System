package com.demo.controllers.admin;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.entities.Cinema;
import com.demo.entities.City;
import com.demo.entities.Food;
import com.demo.entities.Movie;
import com.demo.helpers.FileHelper;
import com.demo.services.CinemaService;
import com.demo.services.CityService;
import com.demo.services.FoodService;
import com.demo.services.MovieService;

@Controller
@RequestMapping({ "admin" })
public class DashboardController {
	@Autowired
	private MovieService movieService;
	@Autowired
	private CityService cityService;
	@Autowired
	private CinemaService cinemaService;
	@Autowired
	private FoodService foodService;

	@RequestMapping(value = { "dashboard" }, method = RequestMethod.GET)
	public String index() {
		return "admin/dashboard";
	}
	
	@RequestMapping(value = { "listfood" }, method = RequestMethod.GET)
	public String ListFood(ModelMap modelMap) {
		modelMap.put("foods", foodService.findAllfood()); 
		return "admin/food/listfood";
	}
	
	@RequestMapping(value = { "listmovie" }, method = RequestMethod.GET)
	public String ListMovie(ModelMap modelMap) {
		modelMap.put("movies", movieService.findAllMovie()); 
		return "admin/movie/listmovie";
	}
	
	

	@RequestMapping(value = { "addmovie" }, method = RequestMethod.GET)
	public String addMovie(ModelMap modelMap) {

		Movie movie = new Movie();

		modelMap.put("phim", movie);
		return "admin/movie/addmovie";
	}

	@RequestMapping(value = "addmovie", method = RequestMethod.POST)
	public String addMovie(@ModelAttribute("phim") Movie movie, RedirectAttributes redirectAttributes,
			@RequestParam("file") MultipartFile file) {
		try {
			if (file != null && file.getSize() > 0) {

				File folderimage = new File(new ClassPathResource(".").getFile().getPath() + "/static/images");
				String filename = FileHelper.generateFileName(file.getOriginalFilename());
				Path path = Paths.get(folderimage.getAbsolutePath() + File.separator + filename);
				System.out.println(folderimage.getAbsolutePath() + File.separator + filename);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				movie.setMovPoster(filename);
			} else {
				movie.setMovPoster("no-image.jpg");
			}
			if (movieService.save(movie)) {
				redirectAttributes.addFlashAttribute("msg", "ok");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Fail or duplicate name");
				return "redirect:/admin/addmovie";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/dashboard";

	}

	@RequestMapping(value = { "addcity" }, method = RequestMethod.GET)
	public String addCity(ModelMap modelMap) {

		City city = new City();
		modelMap.put("thanhpho", city);
		return "admin/city/addcity";
	}

	@RequestMapping(value = { "addcity" }, method = RequestMethod.POST)
	public String Add(@ModelAttribute("thanhpho") City city, RedirectAttributes redirectAttributes) {
		try {
			if (cityService.save(city)) {
				redirectAttributes.addFlashAttribute("msg", "ok");
			} else {
				redirectAttributes.addFlashAttribute("msg", "fail or duplicate name");
				return "redirect:/admin/addcity";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/dashboard";
	}

	@RequestMapping(value = { "addcinema" }, method = RequestMethod.GET)
	public String AddCinema(ModelMap modelMap) {
		Cinema cinema = new Cinema();
		modelMap.put("cinema", cinema);
		modelMap.put("citys", cityService.findAll());
		return "admin/cinema/addcinema";
	}

	@RequestMapping(value = { "addcinema" }, method = RequestMethod.POST)
	public String AddCinema(@ModelAttribute("cinema") Cinema cinema, RedirectAttributes redirectAttributes) {
		try {
			if (cinemaService.save(cinema)) {
				redirectAttributes.addFlashAttribute("msg", "ok");
			} else {
				redirectAttributes.addFlashAttribute("msg", "fail or duplicate name");
				return "redirect:/admin/addcinema";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/dashboard";
	}
	

	@RequestMapping(value = { "addfood" }, method = RequestMethod.GET)
	public String AddFood(ModelMap modelMap) {
		Food food = new Food();
		modelMap.put("food", food);
		return "admin/food/addfood";
	}
	
	@RequestMapping(value = "addfood", method = RequestMethod.POST)
	public String AddFood(@ModelAttribute("food") Food food, RedirectAttributes redirectAttributes,
			@RequestParam("file") MultipartFile file) {
		try {
			if (file != null && file.getSize() > 0) {

				File folderimage = new File(new ClassPathResource(".").getFile().getPath() + "/static/images");
				String filename = FileHelper.generateFileName(file.getOriginalFilename());
				Path path = Paths.get(folderimage.getAbsolutePath() + File.separator + filename);
				System.out.println(folderimage.getAbsolutePath() + File.separator + filename);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				food.setFoodPhoto(filename);
			} else {
				food.setFoodPhoto("no-image.jpg");
			}
			if (foodService.save(food)) {
				redirectAttributes.addFlashAttribute("msg", "ok");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Fail or duplicate name");
				return "redirect:/admin/addfood";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/dashboard";

	}
}
