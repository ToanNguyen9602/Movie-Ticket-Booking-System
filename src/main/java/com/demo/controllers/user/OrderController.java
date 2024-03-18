package com.demo.controllers.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.dtos.OrderSeat;
import com.demo.entities.Seats;
import com.demo.enums.SeatOrderingStatus;
import com.demo.services.FoodService;
import com.demo.services.ShowService;
import com.demo.services.impl.ShowServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("order")
public class OrderController {

	@Autowired
	private ShowService showService;
	
	@Autowired
	private FoodService foodService;
	
	@GetMapping("order-seats")
	public String orderSeats(ModelMap modelMap,
			@RequestParam("showId") Integer showId) {
		modelMap.put("seatPrice", showService.findPrice(showId));
		modelMap.put("seatInformation", showService.findSeatOrderingStatusOfAShow(showId, SeatOrderingStatus.ORDERED)); 
		modelMap.put("service", showService);
		modelMap.put("showId", showId);
		return "order/order-seat";
	}
	
	@PostMapping("order-seats")
	public String orderSeats(@RequestBody OrderSeat orderingSeat, HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<Seats> seats = showService.mapToSeat(orderingSeat);
        seats.forEach(System.out::println);
        return "redirect:/order/order-food";
	}

	@GetMapping("order-food")
	public String foodOrdering(ModelMap modelMap) {
		foodService.findAll_ListFood()
			.stream()
			.forEach(System.out::println);
		modelMap.put("foods", foodService.findAll_ListFood());
		return "order/order-food";
	}
	
	@GetMapping("checkout")
	 public String checkout(ModelMap modelMap) {
		 
		return "order/checkout";
	}
}
