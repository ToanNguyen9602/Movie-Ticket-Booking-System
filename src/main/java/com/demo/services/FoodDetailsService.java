package com.demo.services;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import com.demo.dtos.ShowSeatsDTO;
import com.demo.dtos.ShowSeatsOrderingStatus;
import com.demo.entities.Seats;

import com.demo.entities.Shows;

import com.demo.enums.SeatOrderingStatus;


public interface FoodDetailsService {
	public Integer incomefromFood();
}
