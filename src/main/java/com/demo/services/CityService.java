package com.demo.services;

import java.util.List;

import com.demo.entities.City;
import com.demo.entities.Cinema;

public interface CityService {
	public boolean save(City city);

	public Iterable<City> findAll();

	public List<City> findAll_ListCity();

	public List<Cinema> findCinemasByCityId(int id);

	public City findId(int id);

	public boolean delete(int id);

	public List<City> SearchByCityName(String kw);

}
