package com.demo.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.demo.entities.Blogs;
import com.demo.entities.Cinema;
import com.demo.entities.City;
import com.demo.entities.Hall;
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

	

	

	

}
