package com.demo.controllers.user;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.demo.entities.Role;
import com.demo.services.AccountService;
import com.demo.services.RoleService;

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

	@RequestMapping(value = { "update/{username}" }, method = RequestMethod.GET)
	public String update(ModelMap modelMap, @PathVariable("username") String username, Authentication authentication) {
		username = authentication.getName();
		Account account = accountService.findbyusername(username);
		modelMap.put("account", account);
		return "user/account";

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
}
