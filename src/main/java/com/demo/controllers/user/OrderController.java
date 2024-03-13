package com.demo.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("order")
public class OrderController {

	@GetMapping({"index", ""})
	public String index(ModelMap modelMap) {
		return "order/index";
	}
	
	@GetMapping("order-food")
	public String foodOrdering(ModelMap modelMap) {
		
		return "";
	}
	
	@GetMapping("book-tickets")
	 public String bookTickets(ModelMap modelMap) {
		return "";
	}
}
