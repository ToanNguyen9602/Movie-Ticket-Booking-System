package com.demo.controllers.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.dtos.OrderSeat;
import com.demo.dtos.ShowSeatsOrderingStatus;
import com.demo.entities.Seats;
import com.demo.entities.Shows;
import com.demo.enums.SeatOrderingStatus;
import com.demo.services.FoodService;
import com.demo.services.SeatsService;
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

	@Autowired
	private SeatsService seatsService;
	

	

 // day ha  n?O  do
	@GetMapping("order-seats")
	public String orderSeats(ModelMap modelMap, @RequestParam("showId") Integer showId) {
		modelMap.put("seatPrice", showService.findPrice(showId));
		modelMap.put("seatInformation", showService.findSeatOrderingStatusOfAShow(showId, SeatOrderingStatus.ORDERED));
		modelMap.put("service", showService);
		modelMap.put("showId", showId);

		modelMap.put("seatservice", seatsService);
		Shows show = showService.findShowsbyId(showId);
		Integer hallId = show.getHall().getId();
		modelMap.put("hallId", hallId);
		return "order/order-seat";
	}

	@PostMapping("order-seats")
	public String orderSeats(@RequestBody OrderSeat orderingSeat, HttpServletRequest request) {
		List<Seats> seats = showService.mapToSeat(orderingSeat);
		seats.forEach(System.out::println); 
		return "redirect:order/order-food";

	}

	@GetMapping("order-food")
	public String foodOrdering(ModelMap modelMap) {
		foodService.findAll_ListFood().stream().forEach(System.out::println);
		modelMap.put("foods", foodService.findAll_ListFood());
		return "order/order-food";
	}

	@GetMapping("checkout")
	public String checkout(ModelMap modelMap) {

		return "order/checkout";
	}

	@GetMapping("thank")
	public String Notification(HttpServletRequest request, Model model) {
		String transactionStatus = request.getParameter("vnp_TransactionStatus");
		String paymentCode = request.getParameter("vnp_TmnCode");
		model.addAttribute("status", transactionStatus);
		model.addAttribute("_bill_payment_code", paymentCode);

		return "order/thank";
	}
}