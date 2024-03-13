package com.demo.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping({ "bookseat"})
public class BookSeatController {

	@RequestMapping(value = {"index"}, method = RequestMethod.GET)
	public String index() {
		return "bookseat/index";
	}
	@RequestMapping(value = {"interest"}, method = RequestMethod.GET)
	public String interest() {
		return "bookseat/interest";
	}
	@RequestMapping(value = {"buy"}, method = RequestMethod.GET)
	public String buy() {
		return "bookseat/buy";
	}
	 
}
