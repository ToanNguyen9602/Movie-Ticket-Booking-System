package com.demo.controllers.user;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.demo.entities.Booking;
import com.demo.entities.Role;
import com.demo.helpers.CodeHelper;
import com.demo.repositories.BookingRepository2;
import com.demo.services.AccountService;
import com.demo.services.BookingService;
import com.demo.services.MailService;
import com.demo.services.RoleService;
import com.demo.services.ShowService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping({ "user" })
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private MailService mailService;

	@Autowired
	private Environment environment;

	@Autowired
	private BookingService bookingService;
	@Autowired
	private ShowService showService;

	@RequestMapping(value = { "account" }, method = RequestMethod.GET)
	public String index() {
		return "user/account";
	}

	@RequestMapping(value = { "logout" }, method = RequestMethod.GET)
	public String logout(HttpSession httpSession) {
		httpSession.removeAttribute("username");
		return "redirect:/home/index";
	}

	@RequestMapping(value = { "register" }, method = RequestMethod.GET)
	public String register(ModelMap modelMap) {
		Account account = new Account();
		modelMap.put("account", account);
		return "user/register";
	}

	@RequestMapping(value = { "register" }, method = RequestMethod.POST)
	public String register(@ModelAttribute("account") Account account, RedirectAttributes redirectAttributes) {

		try {
			Role role = roleService.findrolebyid(3);
			Set<Role> roles = new HashSet<>();
			roles.add(role);
			account.setRoles(roles);
			account.setStatus(true);

			account.setPassword(encoder.encode(account.getPassword()));
			if (accountService.checkexistence(account.getUsername())) {
				redirectAttributes.addFlashAttribute("msg", "this username is used");
				return "redirect:/user/register";
			} else if (accountService.checkemail(account.getEmail())) {
				redirectAttributes.addFlashAttribute("msg", "this email is used");
				return "redirect:/user/register";
			} else if (accountService.checkphone(account.getPhone())) {
				redirectAttributes.addFlashAttribute("msg", "this phonenumber is used");
				return "redirect:/user/register";
			} else if (account.getPhone().length() < 10) {
				redirectAttributes.addFlashAttribute("msg", "invalid phonenumber");
				return "redirect:/user/register";
			}

			else {

				if (accountService.save(account)) {

					redirectAttributes.addFlashAttribute("msg", "ok");

				} else {
					redirectAttributes.addFlashAttribute("msg", "fail");
					return "redirect:/user/register";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/user/login";
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, ModelMap modelMap,
			Authentication authentication) {
		if (authentication != null) {

			return "user/accessdenied";
		} else {
			if (error != null) {
				modelMap.put("msg", "You can not access to this page");
			}
			if (logout != null) {
				modelMap.put("msg", "Log out");
			}
			return "user/login";
		}
	}

	@RequestMapping(value = "accessdenied", method = RequestMethod.GET)
	public String accessdenied() {
		return "user/accessdenied";
	}

	@RequestMapping(value = { "/{username}" }, method = RequestMethod.GET)
	public String updateuser(ModelMap modelMap, @PathVariable("username") String username,
			Authentication authentication) {

		if (authentication == null || !authentication.isAuthenticated()) {
			return "redirect:/user/login";
		}
		username = authentication.getName();
		Account account = accountService.findbyusername(username);
		Integer PaidforMovie = accountService.paidForMoviebyAccountId(account.getId());
		Integer PaidforFood = accountService.sumFoodPricesByAccountId(account.getId());

		if (PaidforMovie == null) {
			PaidforMovie = 0;
		}
		if (PaidforFood == null) {
			PaidforFood = 0;
		}
		int total=PaidforMovie+PaidforFood;
		System.out.println("movie: "+PaidforMovie);
		System.out.println("food: "+PaidforFood);
		System.out.println("total: "+total);
		
		modelMap.put("shows", showService.findAllShowsByAccountId(account.getId()));
		
		
		modelMap.put("Paid", total);
		modelMap.put("account", account);

		return "user/account";

	}

//	@RequestMapping(value = { "update/{username}" }, method = RequestMethod.GET)
//	public String update(ModelMap modelMap, @PathVariable("username") String username, Authentication authentication) {
//		username = authentication.getName();
//		Account account = accountService.findbyusername(username);
//		modelMap.put("account", account);
//		return "user/account";
//
//	}

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
				return "redirect:/user/account/" + account.getUsername();
			}

			if (accountService.save(account)) {
				redirectAttributes.addFlashAttribute("msg", "ok");
			} else {
				redirectAttributes.addFlashAttribute("msg", "fail");
				return "user/account";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/account/logout";

	}

	@RequestMapping(value = { "forgetpassword" }, method = RequestMethod.GET)
	public String forgetpassword() {

		return "user/forgetpassword";
	}

	@RequestMapping(value = { "forgetpassword" }, method = RequestMethod.POST)
	public String forgetpassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
		Account account = accountService.findbyemail(email);
		if (account == null) {
			redirectAttributes.addFlashAttribute("msg", "account not found");
			return "redirect:/account/forgetpassword";
		} else {

			try {
				String password = CodeHelper.generate();
				account.setPassword(encoder.encode(password));
				if (accountService.save(account)) {
					String content = "This is your new Password: " + password + " for account: " + account.getUsername()
							+ "<br>Please login and update your password";

					redirectAttributes.addFlashAttribute("msg", "Your new password is sent to your email");
					mailService.Send(environment.getProperty("spring.mail.username"), account.getEmail(),
							"Your new Password", content);

				} else {
					redirectAttributes.addFlashAttribute("msg", "fail");
					return "redirect:/account/forgetpassword";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "redirect:/user/login";
		}
	}

	@RequestMapping(value = { "/history/{username}" }, method = RequestMethod.GET)
	public String history(ModelMap modelMap, @PathVariable("username") String username, Authentication authentication) {

		if (authentication == null || !authentication.isAuthenticated()) {
			return "redirect:/user/login";
		}
		username = authentication.getName();
		Account account = accountService.findbyusername(username);
		modelMap.put("account", account);
		return "user/account";

	}
}
