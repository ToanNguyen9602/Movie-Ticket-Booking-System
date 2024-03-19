package com.demo.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.demo.entities.Account;
import com.demo.entities.Booking;
import com.demo.repositories.BookingDetailRepository;
import com.demo.repositories.BookingRepository2;

import com.demo.services.BookingService;

@Service
public class BookingServiceImp implements BookingService {

	@Autowired
	private BookingRepository2 bookingRepository2;
	@Autowired
	private BookingDetailRepository bookingDetailRepository;

	@Override
	public List<Booking> findBookingbyAccountId(int id) {
		return bookingRepository2.findBookingbyAccountId(id);
	}

	@Override
	public Integer PaidforMovieByAccount(Account account) {
		return bookingDetailRepository.PaidforMovieByAccount(account);
	}

}
