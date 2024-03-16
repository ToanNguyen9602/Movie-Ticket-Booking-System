package com.demo.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.enums.SeatOrderingStatus;
import com.demo.services.ShowService;
import com.demo.services.impl.ShowServiceImpl;

@Controller
@RequestMapping("order")
public class OrderController {

	@Autowired
	private ShowService showService;
	
	@GetMapping("order-seats")
	public String orderSeats(ModelMap modelMap,
			@RequestParam("showId") Integer showId) {
		modelMap.put("seatPrice", showService.findPrice(showId));
		modelMap.put("seatInformation", showService.findSeatOrderingStatusOfAShow(showId, SeatOrderingStatus.ORDERED)); 
		modelMap.put("service", showService);
		return "bookseat/index";
	}
	@GetMapping("order-food")
	public String foodOrdering(ModelMap modelMap) {
		
		return "food/index";
	}
	
	@GetMapping("book-tickets")
	 public String bookTickets(ModelMap modelMap) {
		return "";
	}
}
