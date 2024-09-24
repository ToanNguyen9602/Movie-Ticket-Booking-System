package com.demo.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.demo.entities.Blogs;
import com.demo.entities.Cinema;
import com.demo.entities.City;
import com.demo.entities.Hall;
import com.demo.entities.Seats;
import com.demo.repositories.BlogsRepository;
import com.demo.repositories.CinemaRepository;
import com.demo.repositories.CityRepository;
import com.demo.repositories.HallRepository;
import com.demo.repositories.SeatsRepository;
import com.demo.repositories.ShowsRepository;
import com.demo.services.BlogsService;
import com.demo.services.CityService;
import com.demo.services.HallService;
import com.demo.services.SeatsService;

@Service
public class SeatsServiceImpl implements SeatsService {

	@Autowired
	private SeatsRepository seatsRepository;
	
	@Autowired
	private ShowsRepository showsRepository;

	@Override
	public boolean save(Seats seats) {
		try {
			seatsRepository.save(seats);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public int countseats(int hallid) {

		return seatsRepository.countseatsByHallid(hallid);
	}

	@Override
	public List<Seats> findallSeatsbyHallid(int hallid) {
		return seatsRepository.findallSeatsbyHallid(hallid);
	}

	@Override
	public Integer countUniqueRowsByHallId(int hallId) {
		return seatsRepository.countUniqueRowsByHallId(hallId);
	}

	@Override
	public Integer countUniqueColumnsByHallId(int hallId) {
		return seatsRepository.countUniqueColumnsByHallId(hallId);
	}
	
	@Override
	public boolean delete(int id) {
		try {
			seatsRepository.deleteAll(seatsRepository.findallSeatsbyHallid(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Seats findSeatId(int hallId, String row, int number) {
		return seatsRepository.findSeatId(hallId, row, number);
	}
	

}
