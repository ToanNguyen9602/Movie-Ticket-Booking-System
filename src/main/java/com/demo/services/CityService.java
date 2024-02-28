package com.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.entities.Cinema;
import com.demo.entities.City;

public interface CityService {
	public Iterable<City> findAll();
	public List<Cinema> findCinemasByCityId(int city_id);
}
