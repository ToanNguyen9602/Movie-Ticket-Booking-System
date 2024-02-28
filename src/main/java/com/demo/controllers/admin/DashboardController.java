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

import com.demo.entities.Movie;
import com.demo.helpers.FileHelper;
import com.demo.services.MovieService;
@Controller
@RequestMapping({ "admin" })
public class DashboardController {
	@Autowired
	private MovieService movieService;
	@RequestMapping(value = {"dashboard"}, method = RequestMethod.GET)
	public String index() {
		return "admin/dashboard";
	}
	
	@RequestMapping(value = {"addmovie"}, method = RequestMethod.GET)
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

				movie.setPoster(filename);
			} else {
				movie.setPoster("no-image.jpg");
			}
			if (movieService.save(movie)) {
				redirectAttributes.addFlashAttribute("msg", "ok");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Fail");
				return "redirect:/admin/movie/addmovie";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/dashboard";

	}
	
	
}
