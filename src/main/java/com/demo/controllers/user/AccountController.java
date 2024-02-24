package com.demo.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
	@RequestMapping({ "user"})
public class AccountController {
	
	 
		@RequestMapping(value = {"account"}, method = RequestMethod.GET)
		public String index() {
			return "user/account";
		}
}
