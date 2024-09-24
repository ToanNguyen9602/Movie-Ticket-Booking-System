
package com.demo.controllers.user;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.controllers.vnp.Config;
import com.demo.dtos.BookingDTO;
import com.demo.dtos.FoodBookingDTO;
import com.demo.entities.Account;
import com.demo.entities.Booking;
import com.demo.entities.BookingDetails;
import com.demo.entities.Movie;
import com.demo.helpers.FileHelper;
import com.demo.repositories.BookingDetailRepository;
import com.demo.services.AccountService;
import com.demo.services.BookingDetailsService;
import com.demo.services.BookingService;
import com.demo.services.CityService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
@RestController
public class VnPayController {

	@Autowired
	private CityService cityService;

	@Autowired
	private BookingService bookingService;
	
	@Autowired 
	private AccountService accountService;
	
	@Autowired
	private BookingDetailRepository bookingDetailRepository;

	@RequestMapping(value = "order/api/vnpay/{payment}", method = RequestMethod.POST)
	public String vnpay(HttpServletRequest req, @PathVariable(name = "payment")String payment) throws UnsupportedEncodingException {
		// no day dung ko 
		String vnp_Version = "2.1.0";
		String vnp_Command = "pay";
		String orderType = "other";
		// long amount = Integer.parseInt(req.getParameter("amount"))*100;
		long amount = (long) (Integer.parseInt(payment) * 100);
		String bankCode = req.getParameter("bankCode");

		String vnp_TxnRef = Config.getRandomNumber(8);
		String vnp_IpAddr = Config.getIpAddress(req);

		String vnp_TmnCode = Config.vnp_TmnCode;

		Map<String, String> vnp_Params = new HashMap<>();
		vnp_Params.put("vnp_Version", vnp_Version);
		vnp_Params.put("vnp_Command", vnp_Command);
		vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
		vnp_Params.put("vnp_Amount", String.valueOf(amount));
		vnp_Params.put("vnp_CurrCode", "VND");

		if (bankCode != null && !bankCode.isEmpty()) {
			vnp_Params.put("vnp_BankCode", bankCode);
		}
		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
		vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
		vnp_Params.put("vnp_OrderType", orderType);
		String locate = req.getParameter("language");
		if (locate != null && !locate.isEmpty()) {
			vnp_Params.put("vnp_Locale", locate);
		} else {
			vnp_Params.put("vnp_Locale", "vn");
		}

		String transactionStatus = vnp_Params.get("vnp_TransactionStatus");
		System.out.println(transactionStatus);
		vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
		vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String vnp_CreateDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
		cld.add(Calendar.MINUTE, 15);
		String vnp_ExpireDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
		List fieldNames = new ArrayList(vnp_Params.keySet());
		Collections.sort(fieldNames);
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();
		Iterator itr = fieldNames.iterator();
		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) vnp_Params.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				// Build hash data
				hashData.append(fieldName);
				hashData.append('=');
				hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				// Build query
				query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
				query.append('=');
				query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				if (itr.hasNext()) {
					query.append('&');
					hashData.append('&');
				}
			}
		}
		String queryUrl = query.toString();
		String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
		String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
		com.google.gson.JsonObject job = new JsonObject();
		job.addProperty("code", "00");
		job.addProperty("message", "success");
		job.addProperty("data", paymentUrl);
		Gson gson = new Gson();

		return gson.toJson(job);
	}

	@RequestMapping(value = "/vnpay", method = RequestMethod.GET)
	public String vnpayview(Model model) {
		return "shared/vpcpay";
	}
	// oke ban chay lai di xem no con loi null ko
	@RequestMapping(value = "order/api/savethank", method = RequestMethod.POST)
	public ResponseEntity<Object> savethank(@RequestBody BookingDTO obj,Authentication authentication
			) {
		System.out.println("savethank"); // api chạy vào đây r xử lí tiếp đê , dk a 
		System.out.println(obj.getShowId());
		System.out.println(obj.getSeatIds());
		//System.out.println(obj.getFoods());
		System.out.println(obj.getShowPrice());
		// 1. insert bill mới 
		Account account = accountService.findbyusername(authentication.getName());
		bookingService.save(account, obj);
		// 2. lấy id bill vưa insert ra save
		var billId = 0;
		// 3. tạo list booking detail => insert vào db
		LinkedList<BookingDetails> tickets = new LinkedList<BookingDetails>();;
		
		// 3.1 insert tickets
		// 4. tạo list booking food => insert vào db
		
		// 3.1 insert foods
		//obj.getFoods().forEach(System.out::println); // dòng này code sai
//		Account account = accountService.findbyusername(authentication.getName());
//		bookingService.save(account, booking);
		return new ResponseEntity<Object>(new Object() {
			public String result = "abc";
		}, HttpStatus.OK);
	} 
	
	@GetMapping(value = "order/api/test", produces = "application/json")
	public ResponseEntity<Object> test() {
		return new ResponseEntity<>(new Object(), HttpStatus.OK); 
	}
	

//	@RequestMapping(value = "savethanks", method = RequestMethod.POST)
//	public String savethanks(@ModelAttribute("details") BookingDetails bookingDetails,
//			RedirectAttributes redirectAttributes) { 
//		try {
//			if (bookingDetailsService.save(bookingDetails)) {
//				redirectAttributes.addFlashAttribute("msg", "ok");
//			} else {
//				redirectAttributes.addFlashAttribute("msg", "fail or duplicate name");
//				return "redirect:/admin/addcity";
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "redirect:/admin/listnowmovie";
//
//	}

}
