package com.demo.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("bookseat")
public class BookSeatController {
	@GetMapping({ "index", "" })
	public String index() {
		return "bookseat/index";
	}
	

	@GetMapping("interest")
	public String interest() {
		return "bookseat/interest";
	}

	@GetMapping("buy")
	public String buy() {
		return "bookseat/buy";
	}
	@GetMapping("thank")
	public String thank() {
		return "bookseat/thank";
	}

}
