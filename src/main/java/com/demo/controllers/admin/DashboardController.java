package com.demo.controllers.admin;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.entities.Account;
import com.demo.entities.Cinema;
import com.demo.entities.City;
import com.demo.entities.FoodMenu;
import com.demo.entities.Movie;
import com.demo.entities.Role;
import com.demo.helpers.FileHelper;
import com.demo.services.AccountService;
import com.demo.services.BlogsService;
import com.demo.services.CinemaService;
import com.demo.services.CityService;
import com.demo.services.FoodService;
import com.demo.services.HallService;
import com.demo.services.MovieService;
import com.demo.services.RoleService;

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
	@Autowired
	private BlogsService blogsService;
	@Autowired
	private HallService hallService;
	
	//
	@Autowired
	private AccountService accountService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private BCryptPasswordEncoder encoder;

	@RequestMapping(value = { "dashboard" }, method = RequestMethod.GET)
	public String index() {
		return "admin/dashboard";
	}
	
	@RequestMapping(value = { "listfood" }, method = RequestMethod.GET)
	public String ListFood(ModelMap modelMap) {
		modelMap.put("foods", foodService.findAll_ListFood()); 
		return "admin/food/listfood";
	}
	
	@RequestMapping(value = { "listmovie" }, method = RequestMethod.GET)
	public String ListMovie(ModelMap modelMap) {
		modelMap.put("movies", movieService.findAllMovie()); 
		return "admin/movie/listmovie";
	}
	
	@RequestMapping(value = { "listcity" }, method = RequestMethod.GET)
	public String ListCity(ModelMap modelMap) {
		modelMap.put("citys", cityService.findAll()); 
		return "admin/city/listcity";
	}
	
	@RequestMapping(value = { "listcinema" }, method = RequestMethod.GET)
	public String ListCinema(ModelMap modelMap) {
		modelMap.put("cinemas", cinemaService.findAll()); 
		return "admin/cinema/listcinema";
	}
	@GetMapping(value = "cinema/{cinemaid}")
	public String details(ModelMap modelMap, @PathVariable("cinemaid") int cinemaid) {
		//modelMap.put("halls", hallService.findHallByCinemaId(cinemaid));
		return "admin/hall/listhall";
	}
	
	@RequestMapping(value = { "listblog" }, method = RequestMethod.GET)
	public String ListBlog(ModelMap modelMap) {
		modelMap.put("blogs", blogsService.findByAll()); 
		return "admin/blog/listblog";
	}
	
	@RequestMapping(value = { "listuser" }, method = RequestMethod.GET)
	public String ListUser(ModelMap modelMap) {
		modelMap.put("accounts", accountService.findAllByRole(3)); 
		return "admin/account/listuser";
	}
	
	@RequestMapping(value = { "liststaff" }, method = RequestMethod.GET)
	public String ListStaff(ModelMap modelMap) {
		modelMap.put("accounts", accountService.findAllByRole(2)); 
		return "admin/account/liststaff";
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

				movie.setPoster(filename);
			} else {
				movie.setPoster("no-image.jpg");
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
		return "redirect:/admin/listmovie";

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
		return "redirect:/admin/listcity";
	}

	@RequestMapping(value = { "addcinema" }, method = RequestMethod.GET)
	public String AddCinema(ModelMap modelMap) {
		Cinema cinema = new Cinema();
		modelMap.put("cinema", cinema);
		modelMap.put("citys", cityService.findAll());
		return "admin/cinema/addcinema";
	}

	@RequestMapping(value = "addcinema", method = RequestMethod.POST)
	public String AddCinema(@ModelAttribute("cinema") Cinema cinema, RedirectAttributes redirectAttributes,
			@RequestParam("file") MultipartFile file) {
		try {
			if (file != null && file.getSize() > 0) {

				File folderimage = new File(new ClassPathResource(".").getFile().getPath() + "/static/images");
				String filename = FileHelper.generateFileName(file.getOriginalFilename());
				Path path = Paths.get(folderimage.getAbsolutePath() + File.separator + filename);
				System.out.println(folderimage.getAbsolutePath() + File.separator + filename);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				cinema.setPhoto(filename);
			} else {
				cinema.setPhoto("no-image.jpg");
			}
			if (cinemaService.save(cinema)) {
				redirectAttributes.addFlashAttribute("msg", "ok");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Fail or duplicate name");
				return "redirect:/admin/addcinema";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/listcinema";

	}
	

	@RequestMapping(value = { "addfood" }, method = RequestMethod.GET)
	public String AddFood(ModelMap modelMap) {
		FoodMenu food = new FoodMenu();
		modelMap.put("food", food);
		return "admin/food/addfood";
	}
	
	@RequestMapping(value = "addfood", method = RequestMethod.POST)
	public String AddFood(@ModelAttribute("food") FoodMenu food, RedirectAttributes redirectAttributes,
			@RequestParam("file") MultipartFile file) {
		try {
			if (file != null && file.getSize() > 0) {

				File folderimage = new File(new ClassPathResource(".").getFile().getPath() + "/static/images");
				String filename = FileHelper.generateFileName(file.getOriginalFilename());
				Path path = Paths.get(folderimage.getAbsolutePath() + File.separator + filename);
				System.out.println(folderimage.getAbsolutePath() + File.separator + filename);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				food.setPhoto(filename);
			} else {
				food.setPhoto("no-image.jpg");
			}
			
			food.setStatus(true);
			
			if (foodService.save(food)) {
				redirectAttributes.addFlashAttribute("msg", "ok");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Fail or duplicate name");
				return "redirect:/admin/addfood";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/listfood";

	}
	
	@RequestMapping(value = { "register" }, method = RequestMethod.GET)
	public String register(ModelMap modelMap) {
		Account account = new Account();
		modelMap.put("account", account);
		return "admin/account/register";
	}
	
	@RequestMapping(value = { "register" }, method = RequestMethod.POST)
	public String register(@ModelAttribute("account") Account account, RedirectAttributes redirectAttributes) {

		try {
			Role role = roleService.findrolebyid(2);
			Set<Role> roles = new HashSet<>();
			roles.add(role);
			account.setRoles(roles);
			account.setStatus(true);

			account.setPassword(encoder.encode(account.getPassword()));
			if(accountService.checkexistence(account.getUsername()))
			{
				redirectAttributes.addFlashAttribute("msg", "this username is used");
				return "redirect:/admin/register";
			}
			else if(accountService.checkemail(account.getEmail()))
			{
				redirectAttributes.addFlashAttribute("msg", "this email is used");
				return "redirect:/admin/register";
			}
			else if(accountService.checkphone(account.getPhone()))
			{
				redirectAttributes.addFlashAttribute("msg", "this phonenumber is used");
				return "redirect:/admin/register";
			}
			else if(account.getPhone().length()<10)
			{
				redirectAttributes.addFlashAttribute("msg", "invalid phonenumber");
				return "redirect:/admin/register";
			}
			
			else
			{

			if (accountService.save(account)) {

				redirectAttributes.addFlashAttribute("msg", "ok");

			} else {
				redirectAttributes.addFlashAttribute("msg", "fail");
				return "redirect:/admin/register";
			}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/dashboard";
	}
}
