package com.demo.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.demo.entities.Account;
import com.demo.entities.Booking;

public interface BookingService {

	public List<Booking> findBookingbyAccountId(int id);
	
	public Integer PaidforMovieByAccount(Account account);
}
