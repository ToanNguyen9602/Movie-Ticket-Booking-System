package com.demo.services;

import java.util.List;
import com.demo.entities.Cinema;
import com.demo.entities.Hall;
import com.demo.entities.Movie;

public interface CinemaService {
	
	public List<Cinema> findAll();
	public Cinema findCinemasById(int id);
	boolean save(Cinema cinema);
	List<Cinema> findCinemasFromCityAndMovie(Integer cityId, Integer movieId);
	
	public List<Hall> findHallsByCinemaId(int cinemaid);

}
