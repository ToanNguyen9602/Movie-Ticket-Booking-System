package com.demo.services;

import java.util.List;
import com.demo.entities.Cinema;
import com.demo.entities.Hall;
import com.demo.entities.Movie;

public interface CinemaService {
	
	public List<Cinema> findAll();
	
	public List<Cinema> findAll_ListCinema();
	public Cinema findById(int id);
	public boolean delete(int id);
	boolean save(Cinema cinema);
	List<Cinema> findCinemasFromCityAndMovie(Integer cityId, Integer movieId);
	
	public List<Hall> findHallsByCinemaId(int cinemaid);
	public List<Cinema> findCinemaByCityId(int city_id);
	
	public List<Cinema> SearchByCinemaName(String kw);
}
