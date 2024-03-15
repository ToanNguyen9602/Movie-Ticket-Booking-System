package com.demo.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.demo.entities.Blogs;
import com.demo.entities.Cinema;
import com.demo.entities.City;
import com.demo.entities.Hall;
import com.demo.entities.Seats;
import com.demo.helpers.MapUtils;
import com.demo.repositories.BlogsRepository;
import com.demo.repositories.CinemaRepository;
import com.demo.repositories.CityRepository;
import com.demo.repositories.HallRepository;
import com.demo.services.BlogsService;
import com.demo.services.CityService;
import com.demo.services.HallService;

@Service
public class HallServiceImpl implements HallService {

	@Autowired
	private HallRepository hallRepository;

	@Override
	public List<Hall> findHallsByCinemaId(int cinemaid) {
		// TODO Auto-generated method stub
		return hallRepository.findHallsByCinemaId(cinemaid);
	}

	@Override
	public boolean save(Hall hall) {
		try {
			hallRepository.save(hall);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Hall findHallbyId(int id) {
		// TODO Auto-generated method stub
		return hallRepository.findHallbyId(id);
	}

	@Override
	public Integer saveAndGetId(Hall hall) {
		Hall savedHall = hallRepository.save(hall);
		return savedHall != null ? savedHall.getId() : null;
	}

//	sorted by key asc
	@Override
	public Map<String, Integer> findRowAndMaxColOfTheRow(Integer hallId) {
		var hall = hallRepository.findHallbyId(hallId);
		List<Seats> seats = hall.getSeatses();
		Map<String, Integer> rowAndMaxColNumber = new HashMap();
		seats.forEach(seat -> {
			String row = seat.getRow();
			Integer number = seat.getNumber();
			if (rowAndMaxColNumber.get(row) != null) {
				if(rowAndMaxColNumber.get(row).compareTo(number) < 0) {
					rowAndMaxColNumber.put(row, number);					
				}
			} else {
				rowAndMaxColNumber.put(row, number);
			}
		});
		return MapUtils.captializeKey(MapUtils.sortByKeyString(rowAndMaxColNumber));
	}

	

}
