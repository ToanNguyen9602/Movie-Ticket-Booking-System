package com.demo.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
@RequestMapping({ "admin" })
public class DashboardController {
	@RequestMapping(value = {"dashboard"}, method = RequestMethod.GET)
	public String index() {
		return "admin/dashboard";
	}
	
	@RequestMapping(value = {"addAccount"}, method = RequestMethod.GET)
	public String addAccount() {
		return "admin/account/add";
	}
	
	
}
