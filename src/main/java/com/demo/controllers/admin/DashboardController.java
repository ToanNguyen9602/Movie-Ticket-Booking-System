package com.demo.controllers.admin;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import com.demo.entities.Blogs;
import com.demo.entities.Cinema;
import com.demo.entities.City;
import com.demo.entities.FoodMenu;
import com.demo.entities.Hall;
import com.demo.entities.Movie;
import com.demo.entities.Role;
import com.demo.entities.Seats;
import com.demo.entities.Shows;
import com.demo.helpers.FileHelper;
import com.demo.services.AccountService;
import com.demo.services.BlogsService;
import com.demo.services.BookingDetailsService;
import com.demo.services.CinemaService;
import com.demo.services.CityService;
import com.demo.services.FoodDetailsService;
import com.demo.services.FoodService;
import com.demo.services.HallService;
import com.demo.services.MovieService;
import com.demo.services.RoleService;
import com.demo.services.SeatsService;
import com.demo.services.ShowService;

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
	@Autowired
	private SeatsService seatsService;
	@Autowired
	private ShowService showService;

	@Autowired
	private Environment environment;

	//
	@Autowired
	private AccountService accountService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private BookingDetailsService bookingDetailsService;

	@Autowired
	private FoodDetailsService foodDetailsService;

	@RequestMapping(value = { "dashboard", "index", "" }, method = RequestMethod.GET)
	public String index(ModelMap modelMap) {

		modelMap.put("movieincome", bookingDetailsService.incomefromMovies());
		modelMap.put("foodincome", foodDetailsService.incomefromFood());
		modelMap.put("totalincome", bookingDetailsService.incomefromMovies() + foodDetailsService.incomefromFood());
		modelMap.put("users", accountService.countAccountsWithRoleId(3));
		modelMap.put("countedshows", showService.countShowsEnd());
		modelMap.put("movies", movieService.top5Movies());
		List<Movie> movies = movieService.top5Movies();
		Map<Integer, Integer> sumOfPricesMap = new HashMap<>();
		for (Movie movie : movies) {
			Integer sumOfPrices = bookingDetailsService.sumOfPricesByMovieId(movie.getId());
			sumOfPricesMap.put(movie.getId(), sumOfPrices);
		}
		modelMap.put("sumOfPricesMap", sumOfPricesMap);
		modelMap.put("topaccounts", accountService.top5paid());

		List<Account> topaccounts = accountService.top5paid();
		Map<Integer, Integer> sumOfUserPaidMap = new HashMap<>();

		for (Account account : topaccounts) {
			Integer sumOfUserPaid = accountService.paidForMoviebyAccountId(account.getId())
					+ accountService.sumFoodPricesByAccountId(account.getId());
			sumOfUserPaidMap.put(account.getId(), sumOfUserPaid);
		}
		modelMap.put("sumOfUserPaidMap", sumOfUserPaidMap);

		return "admin/dashboard";
	}

	@RequestMapping(value = { "listfood" }, method = RequestMethod.GET)
	public String listFood(ModelMap modelMap, @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false) String keyword) {
		int pageSize = Integer.parseInt(environment.getProperty("pageSize"));
		Page<FoodMenu> page;

		if (keyword != null) {
			page = foodService.SearchByFoodName(keyword, pageNo, pageSize);
		} else {
			page = foodService.findAll_ListFoodpagin(pageNo, pageSize);
		}

		modelMap.put("foods", page.getContent());
		modelMap.put("currentPage", pageNo);
		modelMap.put("totalPages", page.getTotalPages());
		modelMap.put("keyword", keyword);
		return "admin/food/listfood";
	}

	@RequestMapping(value = { "listnowmovie" }, method = RequestMethod.GET)
	public String ListNowMovie(ModelMap modelMap, @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false) String keyword) {
		int pageSize = Integer.parseInt(environment.getProperty("pageSize"));
		Page<Movie> page;

		if (keyword != null) {
			page = movieService.searchNowShowingMovies(keyword, pageNo, pageSize);
		} else {
			page = movieService.findNowShowingMovies(pageNo, pageSize);
		}

		modelMap.put("movies", page.getContent());
		modelMap.put("currentPage", pageNo);
		modelMap.put("totalPages", page.getTotalPages());
		modelMap.put("keyword", keyword);
		return "admin/movie/listnowmovie";
	}

	@RequestMapping(value = { "listcomingmovie" }, method = RequestMethod.GET)
	public String ListComing_soonMovie(ModelMap modelMap,
			@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false) String keyword) {
		int pageSize = Integer.parseInt(environment.getProperty("pageSize"));
		Page<Movie> page;

		if (keyword != null) {
			page = movieService.searchUpcomingMovies(keyword, pageNo, pageSize);
		} else {
			page = movieService.findUpcomingMovies(pageNo, pageSize);
		}

		modelMap.put("movies", page.getContent());
		modelMap.put("currentPage", pageNo);
		modelMap.put("totalPages", page.getTotalPages());
		modelMap.put("keyword", keyword);
		return "admin/movie/listcomingmovie";
	}

	@RequestMapping(value = { "listcity" }, method = RequestMethod.GET)
	public String ListCity(ModelMap modelMap) {
		modelMap.put("citys", cityService.findAll_ListCity());
		return "admin/city/listcity";
	}

	@RequestMapping(value = { "listcinema" }, method = RequestMethod.GET)
	public String ListCinema(ModelMap modelMap) {
		modelMap.put("cinemas", cinemaService.findAll_ListCinema());
		return "admin/cinema/listcinema";
	}

	@GetMapping(value = "cinema/{id}")
	public String details(ModelMap modelMap, @PathVariable("id") int id) {
		modelMap.put("halls", cinemaService.findHallsByCinemaId(id));
		modelMap.put("cinema", cinemaService.findById(id));
		Map<Integer, Integer> hallSeatsCountMap = new HashMap<>();

		for (Hall hall : cinemaService.findHallsByCinemaId(id)) {
			int hallId = hall.getId();
			int seatCount = seatsService.countseats(hallId);
			hallSeatsCountMap.put(hallId, seatCount);
		}
		modelMap.put("hallSeatsCountMap", hallSeatsCountMap);
		return "admin/hall/listhall";
	}

	@RequestMapping(value = { "listblog" }, method = RequestMethod.GET)
	public String ListBlog(ModelMap modelMap) {
		modelMap.put("blogs", blogsService.findByAllonAdminPage());
		return "admin/blog/listblog";
	}

	@RequestMapping(value = { "listuser" }, method = RequestMethod.GET)
	public String ListUser(ModelMap modelMap) {
		modelMap.put("accounts", accountService.findAllByRoles(3));

		return "admin/account/listuser";
	}

	@RequestMapping(value = { "liststaff" }, method = RequestMethod.GET)
	public String ListStaff(ModelMap modelMap) {
		modelMap.put("accounts", accountService.findAllByRoles(2));

		return "admin/account/liststaff";
	}

	@RequestMapping(value = { "searchbyusername/staff" }, method = RequestMethod.GET)
	public String Staffsearchbyusername(@RequestParam("kw") String kw, ModelMap modelMap) {
		modelMap.put("accounts", accountService.findAccount(kw, 2));
		modelMap.put("kw", kw);

		return "admin/account/liststaff";
	}

	@RequestMapping(value = { "searchbyusername/user" }, method = RequestMethod.GET)
	public String Usersearchbyusername(@RequestParam("kw") String kw, ModelMap modelMap) {
		modelMap.put("accounts", accountService.findAccount(kw, 3));
		modelMap.put("kw", kw);

		return "admin/account/listuser";
	}

//	@RequestMapping(value = { "listuser" }, method = RequestMethod.GET)
//	public String Listuser(ModelMap modelMap, @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
//			@RequestParam(required = false) String keyword) {
//		int pageSize = Integer.parseInt(environment.getProperty("pageSize"));
//		Page<Account> page;
//
//		if (keyword != null) {
//			page = accountService.findAccount(keyword, 3, pageNo, pageSize);
//		} else {
//			page = accountService.findAllByRole(3, pageNo, pageSize);
//		}
//
//		modelMap.put("accounts", page.getContent());
//		modelMap.put("currentPage", pageNo);
//		modelMap.put("totalPages", page.getTotalPages());
//		modelMap.put("keyword", keyword);
//		return "admin/account/listuser";
//	}

//	@RequestMapping(value = { "liststaff" }, method = RequestMethod.GET)
//	public String ListStaff(ModelMap modelMap, @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
//			@RequestParam(required = false) String keyword) {
//		int pageSize = Integer.parseInt(environment.getProperty("pageSize"));
//		Page<Account> page;
//
//		if (keyword != null) {
//			page = accountService.findAccount(keyword, 2, pageNo, pageSize);
//		} else {
//			page = accountService.findAllByRole(2, pageNo, pageSize);
//		}
//
//		modelMap.put("accounts", page.getContent());
//		modelMap.put("currentPage", pageNo);
//		modelMap.put("totalPages", page.getTotalPages());
//		modelMap.put("keyword", keyword);
//		return "admin/account/liststaff";
//	}

	@RequestMapping(value = { "addblog" }, method = RequestMethod.GET)
	public String addBlog(ModelMap modelMap) {

		Blogs blog = new Blogs();
		modelMap.put("blog", blog);
		return "admin/blog/addblog";
	}

	@RequestMapping(value = "addblog", method = RequestMethod.POST)
	public String addBlog(@ModelAttribute("blog") Blogs blogs, RedirectAttributes redirectAttributes,
			Authentication authentication,

			@RequestParam("file") MultipartFile file) {
		try {
			if (file != null && file.getSize() > 0) {

				File folderimage = new File(new ClassPathResource(".").getFile().getPath() + "/static/images");
				String filename = FileHelper.generateFileName(file.getOriginalFilename());
				Path path = Paths.get(folderimage.getAbsolutePath() + File.separator + filename);
				System.out.println(folderimage.getAbsolutePath() + File.separator + filename);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				blogs.setPhoto(filename);
			} else {
				blogs.setPhoto("no-image.jpg");
			}

			blogs.setStatus(true);

			if (blogs.getCreated() == null) {
				blogs.setCreated(new Date());
			}
			Account account = accountService.findbyusername(authentication.getName());
			blogs.setAccount(account);
			if (blogsService.save(blogs)) {
				redirectAttributes.addFlashAttribute("msg", "ok");

			} else {
				redirectAttributes.addFlashAttribute("msg", "Fail");

				return "redirect:/admin/addblog";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/listblog";

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

			if (movieService.save2(movie)) {
				redirectAttributes.addFlashAttribute("msg", "ok");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Fail or duplicate name");
				return "redirect:/admin/addmovie";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/listnowmovie";

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

	@RequestMapping(value = { "food/action/{id}" }, method = RequestMethod.GET)
	public String deletefood(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {

		FoodMenu foodMenu = foodService.find(id);
		if (foodMenu.isStatus()) {
			foodMenu.setStatus(false);
		} else {
			foodMenu.setStatus(true);
		}

		if (foodService.save2(foodMenu)) {
			redirectAttributes.addFlashAttribute("msg", "ok");

		} else {
			redirectAttributes.addFlashAttribute("msg", "fail");

		}
		return "redirect:/admin/listfood";
	}

	@RequestMapping(value = "food/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("food", foodService.find(id));
		return "admin/food/edit";
	}

	@RequestMapping(value = "food/edit", method = RequestMethod.POST)
	public String editfood(@ModelAttribute("food") FoodMenu food, @RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		try {
			if (file != null && file.getSize() > 0) {
				File folderImage = new File(new ClassPathResource(".").getFile().getPath() + "/static/images");
				String fileName = FileHelper.generateFileName(file.getOriginalFilename());
				Path path = Paths.get(folderImage.getAbsolutePath() + File.separator + fileName);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				food.setPhoto(fileName);
			}
			food.setStatus(true);
			if (foodService.save2(food)) {
				redirectAttributes.addFlashAttribute("msg", "Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "No Update Duplicate Name");
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/listfood";
	}

	@RequestMapping(value = "city/edit/{id}", method = RequestMethod.GET)
	public String editcity(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("city", cityService.findId(id));
		return "admin/city/edit";
	}

	@RequestMapping(value = "city/edit", method = RequestMethod.POST)
	public String editcity(@ModelAttribute("city") City city, RedirectAttributes redirectAttributes) {
		try {

			if (cityService.save(city)) {
				redirectAttributes.addFlashAttribute("msg", "Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "No Update Duplicate Name");
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/listcity";
	}

	@RequestMapping(value = { "city/delete/{id}" }, method = RequestMethod.GET)
	public String deleteCity(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {

		City city = cityService.findId(id);
		if (city.getCinemas().isEmpty()) {
			cityService.delete(id);
			redirectAttributes.addFlashAttribute("msg", "ok");
		} else {
			redirectAttributes.addFlashAttribute("msg", "fail");

		}
		return "redirect:/admin/listcity";
	}

	@RequestMapping(value = "cinema/edit/{id}", method = RequestMethod.GET)
	public String editcinema(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("cinema", cinemaService.findById(id));
		modelMap.put("citys", cityService.findAll());
		return "admin/cinema/edit";
	}

	@RequestMapping(value = "cinema/edit", method = RequestMethod.POST)
	public String editcinema(@ModelAttribute("cinema") Cinema cinema, @RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		try {
			if (file != null && file.getSize() > 0) {
				File folderImage = new File(new ClassPathResource(".").getFile().getPath() + "/static/images");
				String fileName = FileHelper.generateFileName(file.getOriginalFilename());
				Path path = Paths.get(folderImage.getAbsolutePath() + File.separator + fileName);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				cinema.setPhoto(fileName);
			}
			if (cinemaService.save(cinema)) {
				redirectAttributes.addFlashAttribute("msg", "Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "No Update Duplicate Name");
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/listcinema";
	}

	@RequestMapping(value = { "cinema/delete/{id}" }, method = RequestMethod.GET)
	public String deleteCinema(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {

		Cinema cinema = cinemaService.findById(id);
		if (cinema.getHalls().isEmpty() || cinema.getShowses().isEmpty()) {
			cinemaService.delete(id);
			redirectAttributes.addFlashAttribute("msg", "ok");
		} else {
			redirectAttributes.addFlashAttribute("msg", "fail");

		}
		return "redirect:/admin/listcinema";
	}

	@RequestMapping(value = "movie/edit/{id}", method = RequestMethod.GET)
	public String editmovie(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("movie", movieService.findMovieById(id));
		return "admin/movie/edit";
	}

	@RequestMapping(value = "movie/edit", method = RequestMethod.POST)
	public String editmovie(@ModelAttribute("movie") Movie movie, @RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		try {
			if (file != null && file.getSize() > 0) {
				File folderImage = new File(new ClassPathResource(".").getFile().getPath() + "/static/images");
				String fileName = FileHelper.generateFileName(file.getOriginalFilename());
				Path path = Paths.get(folderImage.getAbsolutePath() + File.separator + fileName);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				movie.setPoster(fileName);
			}
			if (movieService.save(movie)) {
				redirectAttributes.addFlashAttribute("msg", "Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "No Update Duplicate Name");
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/listnowmovie";
	}

	@RequestMapping(value = "movie/edit2/{id}", method = RequestMethod.GET)
	public String editmovie2(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("movie", movieService.findMovieById(id));
		return "admin/movie/edit";
	}

	@RequestMapping(value = "movie/edit2", method = RequestMethod.POST)
	public String editmovie2(@ModelAttribute("movie") Movie movie, @RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		try {
			if (file != null && file.getSize() > 0) {
				File folderImage = new File(new ClassPathResource(".").getFile().getPath() + "/static/images");
				String fileName = FileHelper.generateFileName(file.getOriginalFilename());
				Path path = Paths.get(folderImage.getAbsolutePath() + File.separator + fileName);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				movie.setPoster(fileName);
			}
			if (movieService.save(movie)) {
				redirectAttributes.addFlashAttribute("msg", "Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "No Update Duplicate Name");
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/listcomingmovie";
	}

	@RequestMapping(value = { "movie/delete/{id}" }, method = RequestMethod.GET)
	public String deleteMovie(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {

		Movie checkedmovie = movieService.findMovieById(id);
		if (!checkedmovie.getShowses().isEmpty()) {

			redirectAttributes.addFlashAttribute("msg", "fail");
			return "redirect:/admin/listnowmovie";
		} else {
			movieService.delete(id);
			redirectAttributes.addFlashAttribute("msg", "ok");
		}
		return "redirect:/admin/listnowmovie";
	}

	@RequestMapping(value = { "movie/delete2/{id}" }, method = RequestMethod.GET)
	public String deleteMovie2(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {

		Movie checkedmovie = movieService.findMovieById(id);
		if (!checkedmovie.getShowses().isEmpty()) {

			redirectAttributes.addFlashAttribute("msg", "fail");
			return "redirect:/admin/listcomingmovie";
		} else {
			movieService.delete(id);
			redirectAttributes.addFlashAttribute("msg", "ok");
		}
		return "redirect:/admin/listcomingmovie";
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
			if (accountService.checkexistence(account.getUsername())) {
				redirectAttributes.addFlashAttribute("msg", "this username is used");
				return "redirect:/admin/register";
			} else if (accountService.checkemail(account.getEmail())) {
				redirectAttributes.addFlashAttribute("msg", "this email is used");
				return "redirect:/admin/register";
			} else if (accountService.checkphone(account.getPhone())) {
				redirectAttributes.addFlashAttribute("msg", "this phonenumber is used");
				return "redirect:/admin/register";
			} else if (account.getPhone().length() < 10) {
				redirectAttributes.addFlashAttribute("msg", "invalid phonenumber");
				return "redirect:/admin/register";
			}

			else {

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
		return "redirect:/admin/liststaff";
	}

	@RequestMapping(value = { "update/{username}" }, method = RequestMethod.GET)
	public String update(ModelMap modelMap, @PathVariable("username") String username, Authentication authentication) {
		username = authentication.getName();
		Account account = accountService.findbyusername(username);
		modelMap.put("account", account);
		return "admin/account/update";

	}

	@RequestMapping(value = { "update" }, method = RequestMethod.POST)
	public String update(@ModelAttribute("account") Account account, RedirectAttributes redirectAttributes) {
		try {
			Account existingAccount = accountService.findbyusername(account.getUsername());
			Set<Role> existingRoles = existingAccount.getRoles();

			account.setRoles(existingRoles);

			if (account.getPassword().isEmpty()) {
				account.setPassword(accountService.getpassword(account.getUsername()));
			} else {
				account.setPassword(encoder.encode(account.getPassword()));
			}
			if (account.getFullname().isEmpty() || account.getFullname().isBlank() || account.getAddress().isEmpty()
					|| account.getAddress().isBlank() || account.getEmail().isBlank() || account.getEmail().isEmpty()
					|| account.getPhone().isBlank() || account.getPhone().isEmpty()) {
				redirectAttributes.addFlashAttribute("msg", "fullname, email, phone and address cant be empty");
				return "redirect:/admin/update/" + account.getUsername();
			}

			if (accountService.save(account)) {
				redirectAttributes.addFlashAttribute("msg", "ok");
			} else {
				redirectAttributes.addFlashAttribute("msg", "fail");
				return "admin/update";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/account/logout";

	}

	@RequestMapping(value = { "addhall/{cinemaid}" }, method = RequestMethod.GET)
	public String AddHall(ModelMap modelMap, @PathVariable("cinemaid") int cinemaid) {
		Hall hall = new Hall();
		Seats seats = new Seats();
		modelMap.put("hall", hall);
		// modelMap.put("seats", seats);
		return "admin/hall/addhall";
	}

	@RequestMapping(value = "addhall/{cinemaid}", method = RequestMethod.POST)
	public String AddHall(@ModelAttribute("hall") Hall hall, RedirectAttributes redirectAttributes, ModelMap modelMap,
			@PathVariable("cinemaid") int cinemaid, @RequestParam("numberOfSeats") int numberOfRows,
			@RequestParam("numberOfColumns") int numberOfColumns) {
		try {

			char[] charArray = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
					'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
			Cinema cinema = cinemaService.findById(cinemaid);
			hall.setCinema(cinema);
			Integer hallid = hallService.saveAndGetId(hall);

			if (hallid != null) {

				Hall savedhall = hallService.findHallbyId(hallid);
				for (int i = 0; i < numberOfRows; i++) {
					char selectedRow = charArray[i];

					for (int number = 1; number <= numberOfColumns; number++) {
						Seats newSeat = new Seats(hall, String.valueOf(selectedRow), number);
						seatsService.save(newSeat); // Assuming you have a method in your service to save Seats objects
					}
				}
				redirectAttributes.addFlashAttribute("msg", "ok");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Fail");
				return "redirect:/admin/addhall/" + cinemaid;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/cinema/" + cinemaid;

	}

	@RequestMapping(value = { "edithall/{id}/{cinemaid}" }, method = RequestMethod.GET)
	public String EditHall(ModelMap modelMap, @PathVariable("id") int id, @PathVariable("cinemaid") int cinemaid) {
		Hall hall = hallService.findHallbyId(id);
		modelMap.put("hall", hall);
		System.out.println("result of rows: " + seatsService.countUniqueRowsByHallId(id));
		System.out.println("result of columns: " + seatsService.countUniqueColumnsByHallId(id));
		return "admin/hall/edit";
	}

	@RequestMapping(value = "edithall/{hallid}", method = RequestMethod.POST)
	public String updateHall(@ModelAttribute("hall") Hall hall, RedirectAttributes redirectAttributes,
			ModelMap modelMap, @PathVariable("hallid") int hallid, @RequestParam("numberOfSeats") int numberOfRows,
			@RequestParam("numberOfColumns") int numberOfColumns) {
		try {
			Hall existingHall = hallService.findHallbyId(hallid);

			if (existingHall != null) {
				char[] charArray = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
						'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

				int lastCharofRows = seatsService.countUniqueRowsByHallId(hallid);
				int biggestnumberColumns = seatsService.countUniqueColumnsByHallId(hallid);
				// numberOfColumns = 0;
				for (int i = 0; i < numberOfRows; i++) {
					char selectedRow = charArray[lastCharofRows + i];
					for (int j = 1; j <= biggestnumberColumns; j++) {
						Seats newSeat = new Seats(hall, String.valueOf(selectedRow), j);
						seatsService.save(newSeat);
					}
				}

//				for (int i = 0; i < numberOfColumns + biggestnumberColumns; i++) {
//					char selectedColumns = charArray[lastCharofRows + i];
//					for (int j = 1; j <= biggestnumberColumns + numberOfColumns; j++) {
//						Seats newSeat = new Seats(hall, String.valueOf(selectedColumns), j + biggestnumberColumns);
//						seatsService.save(newSeat);
//					}
//				}

				redirectAttributes.addFlashAttribute("msg", "ok");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Fail");
				return "redirect:/admin/edithall/" + hallid;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/cinema/" + 1;
	}

	@RequestMapping(value = { "blogs/delete/{id}" }, method = RequestMethod.GET)
	public String delete(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
		if (blogsService.delete(id)) {
			redirectAttributes.addFlashAttribute("msg", "ok");

		} else {
			redirectAttributes.addFlashAttribute("msg", "fail");

		}
		return "redirect:/admin/listblog";
	}

	@RequestMapping(value = { "blogs/action/{id}" }, method = RequestMethod.GET)
	public String action(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {

		Blogs blog = blogsService.findByIdonAdminPage(id);
		if (blog.isStatus()) {
			blog.setStatus(false);
		} else {
			blog.setStatus(true);
		}

		if (blogsService.save(blog)) {
			redirectAttributes.addFlashAttribute("msg", "ok");

		} else {
			redirectAttributes.addFlashAttribute("msg", "fail");

		}
		return "redirect:/admin/listblog";
	}

	@RequestMapping(value = { "blogs/edit/{id}" }, method = RequestMethod.GET)
	public String edit(ModelMap modelMap, @PathVariable("id") int id) {
		Blogs blog = blogsService.findByIdonAdminPage(id);
		modelMap.put("blog", blog);
		return "admin/blog/editblog";
	}

	@RequestMapping(value = "blog/edit", method = RequestMethod.POST)
	public String edit(@ModelAttribute("blog") Blogs blogs, RedirectAttributes redirectAttributes,
			Authentication authentication,

			@RequestParam("file") MultipartFile file) {
		try {
			if (file != null && file.getSize() > 0) {

				File folderimage = new File(new ClassPathResource(".").getFile().getPath() + "/static/images");
				String filename = FileHelper.generateFileName(file.getOriginalFilename());
				Path path = Paths.get(folderimage.getAbsolutePath() + File.separator + filename);
				System.out.println(folderimage.getAbsolutePath() + File.separator + filename);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				blogs.setPhoto(filename);
			} else {
				blogs.setPhoto(blogs.getPhoto());
			}

			if (blogs.getCreated() == null) {
				blogs.setCreated(new Date());
			}
			blogs.setUpdated(new Date());
			Account account = accountService.findbyusername(authentication.getName());
			blogs.setAccount(account);
			if (blogsService.save(blogs)) {
				redirectAttributes.addFlashAttribute("msg", "ok");

			} else {
				redirectAttributes.addFlashAttribute("msg", "Fail");

				return "redirect:/admin/editblog";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/listblog";

	}

	@RequestMapping(value = { "account/staff/{id}" }, method = RequestMethod.GET)
	public String Setstatus1(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {

		Account account = accountService.find(id);
		if (account.isStatus()) {
			account.setStatus(false);
		} else {
			account.setStatus(true);
		}

		if (accountService.save(account)) {
			redirectAttributes.addFlashAttribute("msg", "ok");

		} else {
			redirectAttributes.addFlashAttribute("msg", "fail");

		}
		return "redirect:/admin/liststaff";
	}

	@RequestMapping(value = { "account/user/{id}" }, method = RequestMethod.GET)
	public String Setstatus2(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {

		Account account = accountService.find(id);
		if (account.isStatus()) {
			account.setStatus(false);
		} else {
			account.setStatus(true);
		}

		if (accountService.save(account)) {
			redirectAttributes.addFlashAttribute("msg", "ok");

		} else {
			redirectAttributes.addFlashAttribute("msg", "fail");

		}
		return "redirect:/admin/listuser";
	}

	@RequestMapping(value = { "blogsearch" }, method = RequestMethod.GET)
	public String blogsearch(@RequestParam("title") String title, ModelMap modelMap) {
		List<Blogs> blogs = blogsService.searchblogs(title);
		for (Blogs blog : blogs) {
			System.out.println("id " + blog.getId());
			System.out.println("title:" + blog.getTitle());
		}
		modelMap.put("blogs", blogsService.searchblogs(title));
		modelMap.put("kw", title);
		return "admin/blog/listblog";
	}

	@RequestMapping(value = { "searchbycity" }, method = RequestMethod.GET)
	public String searchbycity(@RequestParam("kw") String kw, ModelMap modelMap) {
		modelMap.put("citys", cityService.SearchByCityName(kw));
		modelMap.put("kw", kw);
		return "admin/city/listcity";
	}

	@RequestMapping(value = { "searchbycinema" }, method = RequestMethod.GET)
	public String searchbycinema(@RequestParam("kw") String kw, ModelMap modelMap) {
		modelMap.put("cinemas", cinemaService.SearchByCinemaName(kw));
		modelMap.put("kw", kw);
		return "admin/cinema/listcinema";
	}

	@RequestMapping(value = "addshows", method = RequestMethod.GET)
	public String testaddshow(ModelMap modelMap) {
		Shows show = new Shows();
		modelMap.put("show", show);
		return "admin/shows/index";
	}

	@RequestMapping(value = "shows/{movie_id}", method = RequestMethod.GET)
	public String ShowsbyMovie_id(ModelMap modelMap, @PathVariable("movie_id") int movie_id) {
		modelMap.put("movie", movieService.findMovieById(movie_id));
		modelMap.put("shows", showService.FindShowsbyMovieid(movie_id));
		modelMap.put("cities", cityService.findAll_ListCity());
		return "admin/shows/index";
	}

	@RequestMapping(value = { "addshow/{movieid}" }, method = RequestMethod.GET)
	public String addshow(ModelMap modelMap, @PathVariable("movieid") int movieid) {
		Shows show = new Shows();
		Movie movie = movieService.findMovieById(movieid);

		// modelMap.put("cinemas", cinemaService.f);
		modelMap.put("cities", cityService.findAllCityDTO());
		// modelMap.put("halss", hallService.findHallsByCinemaId(movieid));
		modelMap.put("show", show);
		modelMap.put("movie", movie);

		return "admin/shows/addshow";
	}

	@RequestMapping(value = { "addshow/{movieid}" }, method = RequestMethod.POST)
	public String addshow(@ModelAttribute("shows") Shows show, @RequestParam("starttime") String starttime,
			@PathVariable("movieid") int movieid, RedirectAttributes redirectAttributes,
			@RequestParam("selectedCinemaId") Integer selectedCinemaId,
			@RequestParam("selectedHallId") Integer selectedHallId

	) {
		try {
			Movie movie = movieService.findMovieById(movieid);
			show.setMovie(movie);

			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			Date start = inputFormat.parse(starttime);

			// set starttime
			show.setStartTime(start);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(start);

			// starttime + movie.duration
			calendar.add(Calendar.MINUTE, movie.getDuration());
			// set endtime
			show.setEndTime(calendar.getTime());
			Cinema cinema = cinemaService.findById(selectedCinemaId);
			Hall hall = hallService.findHallbyId(selectedHallId);
			show.setCinema(cinema);
			show.setMovie(movie);
			show.setHall(hall);
			System.out.println("movie: " + movie.getTitle());
			System.out.println("start: " + show.getStartTime());
			System.out.println("end: " + show.getEndTime());
			System.out.println("cinema: " + cinema.getName());
			System.out.println("hall: " + hall.getId());
			movieid = movie.getId();

			LocalDateTime localDateTime = LocalDateTime.parse(starttime);
			localDateTime = localDateTime.truncatedTo(ChronoUnit.MINUTES);
			Date startTimeDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
			Shows checkedshow = showService.FindShowByTimeandHall(show.getHall().getId(), startTimeDate);

			if (checkedshow != null) {
				redirectAttributes.addFlashAttribute("msg", "This Hall has a show at that time");
			} else {
				if (showService.save(show)) {
					redirectAttributes.addFlashAttribute("msg", "ok");

				} else {
					redirectAttributes.addFlashAttribute("msg", "fail");
					return "redirect:admin/shows/" + movieid;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/shows/" + movieid;
	}

	@RequestMapping(value = { "shows/delete/{id}" }, method = RequestMethod.GET)
	public String showdelete(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
		Shows show = showService.findShowsbyId(id);
		int movieid = show.getMovie().getId();
		if (show.getBookingDetailses().isEmpty()) {
			if (showService.delete(id)) {
				redirectAttributes.addFlashAttribute("msg", "ok");
			} else {
				redirectAttributes.addFlashAttribute("msg", "fail");
			}

		} else {
			redirectAttributes.addFlashAttribute("msg", "cant delete this show");

		}
		return "redirect:/admin/shows/" + movieid;
	}

	@RequestMapping(value = "shows/update/{showId}", method = RequestMethod.GET)
	public String UpdateShows(ModelMap modelMap, @PathVariable("showId") int showId) {
		Shows show = showService.findShowsbyId(showId);
		Hall hall = hallService.findHallbyId(show.getHall().getId());
		Movie movie = movieService.findMovieById(show.getMovie().getId());
		City city = cityService.findId(show.getCinema().getCity().getId());
		modelMap.put("show", show);
		modelMap.put("city", city);
		modelMap.put("movie", movie);
		modelMap.put("cinema", cinemaService.findById(show.getCinema().getId()));
		modelMap.put("halls", hallService.findHallsByCinemaId(show.getCinema().getId()));

		return "admin/shows/update";

	}

	@RequestMapping(value = "shows/update", method = RequestMethod.POST)
	public String UpdateShows(@ModelAttribute("show") Shows show, RedirectAttributes redirectAttributes,
			@RequestParam("starttime") String starttime) {
		int movieid = showService.findShowsbyId(show.getId()).getMovie().getId();
		try {

			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			Date start = inputFormat.parse(starttime);
			show.setStartTime(start);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(start);
			show.setMovie(showService.findShowsbyId(show.getId()).getMovie());
			calendar.add(Calendar.MINUTE, showService.findShowsbyId(show.getId()).getMovie().getDuration());
			show.setEndTime(calendar.getTime());
			show.setCinema(showService.findShowsbyId(show.getId()).getCinema());

			LocalDateTime localDateTime = LocalDateTime.parse(starttime);
			localDateTime = localDateTime.truncatedTo(ChronoUnit.MINUTES);
			Date startTimeDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
			Shows checkedshow = showService.FindShowByTimeandHall(show.getHall().getId(), startTimeDate);
			if (checkedshow != null) {
				redirectAttributes.addFlashAttribute("msg", "This Hall has a show at that time");
			} else {

				if (showService.save(show)) {
					System.out.println("startitme saved: " + show.getStartTime());
					redirectAttributes.addFlashAttribute("msg", "ok");

				} else {
					redirectAttributes.addFlashAttribute("msg", "fail");
					return "redirect:/admin/listcinema";

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());

		}

		return "redirect:/admin/shows/" + movieid;

	}

	@RequestMapping(value = { "searchshows" }, method = RequestMethod.GET)
	public String searchshows(ModelMap modelMap,
			@RequestParam(value = "selectedCinemaId", required = false) Integer selectedCinemaId,
			@RequestParam("selectedMovieId") Integer selectedMovieId,
			@RequestParam(value = "startdate", required = false) String startdate) {
		if (startdate == null) {
			if (selectedCinemaId == null) {
				modelMap.put("error", "Please select both city and cinema.");

			} else {
				List<Shows> shows = showService.SearchShowsNoDate(selectedMovieId, selectedCinemaId);
				modelMap.put("shows", shows);
			}

		} else {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date formattedStartDate = dateFormat.parse(startdate);
				List<Shows> shows = showService.SearchShows(selectedMovieId, selectedCinemaId, formattedStartDate);
				modelMap.put("shows", shows);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		modelMap.put("cities", cityService.findAll_ListCity());
		modelMap.put("movie", movieService.findMovieById(selectedMovieId));
		modelMap.put("selectedMovieId", selectedMovieId);
		return "admin/shows/searchresult";
	}

	@RequestMapping(value = { "user/details/{id}" }, method = RequestMethod.GET)
	public String userDetails(ModelMap modelMap, @PathVariable("id") int id) {
		modelMap.put("shows", showService.findAllShowsByAccountId(id));
		modelMap.put("account", accountService.find(id));
		return "admin/account/details";

	}

	@RequestMapping(value = { "deletehall/{id}" }, method = RequestMethod.GET)
	public String halldelete(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
		Hall hall = hallService.findHallbyId(id);
		int cinemaId = hall.getCinema().getId();
		if (hall.getShows().isEmpty() || hall.getShows() == null) {
			try {
				if (hall.getSeatses() != null) {
					seatsService.delete(id);
				}

				if (hallService.delete(id)) {
					redirectAttributes.addFlashAttribute("msg", "ok");
				} else {
					redirectAttributes.addFlashAttribute("msg", "fail");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			redirectAttributes.addFlashAttribute("msg", "cant delete this Hall because it has shows");

		}
		return "redirect:/admin/cinema/" + cinemaId;
	}

}
