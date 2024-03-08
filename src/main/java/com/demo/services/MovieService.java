package com.demo.services;

import java.util.Date;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.demo.MovieStatus;
import com.demo.entities.Cinema;
import com.demo.entities.Hall;
import com.demo.entities.Movie;
import com.demo.entities.Role;
import com.demo.entities.Shows;

public interface MovieService {
	boolean save(Movie movie);

	List<Movie> findAll();
	
	List<Movie> findAll(Integer cinemaId);

	List<Movie> findAll(Integer cinemaId, MovieStatus STATUS);

	List<Movie> searchMoviesByTitle(String title);
	
	Movie findMovieById(int id);
	
	List<Shows> findShowsFromCinemaAndMovie(Integer cinemaId, Integer movieId, Date date);

}
