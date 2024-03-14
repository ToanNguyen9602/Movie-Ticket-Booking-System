package com.demo.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.services.BlogsService;

@Controller
@RequestMapping({ "blog" })
public class BlogController {
	@Autowired
	private BlogsService blogsService;

	@RequestMapping(value = { "index" }, method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		modelMap.put("blogs", blogsService.findByAll());
		return "blog/index";
	}
	
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.GET)
	public String details(ModelMap modelMap, @PathVariable("id") int id) {
		modelMap.put("blog", blogsService.findById(id));
		return "blog/details";
	}

}
