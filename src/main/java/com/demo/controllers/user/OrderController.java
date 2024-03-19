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
	@GetMapping("savethank")
	@ResponseBody
	public Map<String, Object> savethank(HttpServletRequest request, Model model,HttpSession httpSession,
			@RequestParam("showPrice") String showPrice, 
			@RequestParam("showId") String showId, 
			@RequestParam("foods") String foods, 
			@RequestParam("seatIds") String seatIds){
		httpSession.setAttribute("showPrice", showPrice);
		httpSession.setAttribute("showId", showId);
		httpSession.setAttribute("foods", foods);
		httpSession.setAttribute("seatIds", seatIds);
		 Map<String, Object> response = new HashMap<>();
		    response.put("status", "success");
		    response.put("message", "Data saved successfully");

		    return response;
		
	}
	@GetMapping("thank")
	public String Notification(HttpServletRequest request, ModelMap modelMap, @RequestParam("showId") Integer showId, 
			@PathVariable("id") int id){
		String transactionStatus = request.getParameter("vnp_TransactionStatus");
		String paymentCode = request.getParameter("vnp_TmnCode");
		modelMap.addAttribute("status", transactionStatus);
		modelMap.addAttribute("_bill_payment_code", paymentCode); 
		
		modelMap.put("seatPrice", showService.findPrice(showId));
		modelMap.put("seatInformation", showService.findSeatOrderingStatusOfAShow(showId, SeatOrderingStatus.ORDERED)); 
		modelMap.put("service", showService);
		modelMap.put("showId", showId);
		modelMap.put("foods", foodService.find(id));
		return "order/thank";
	}
}
