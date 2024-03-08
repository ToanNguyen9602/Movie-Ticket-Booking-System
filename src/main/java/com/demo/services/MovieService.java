package com.demo.services;

import java.util.Date;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.demo.entities.Cinema;
import com.demo.entities.Hall;
import com.demo.entities.Movie;
import com.demo.entities.MovieShow;
import com.demo.entities.Role;
import com.demo.entities.Shows;




public interface MovieService {
	boolean save(Movie movie);

	List<Movie> findAllMovie();
	
	List<Movie> searchMoviesByTitle(String title);
	
	Movie findMovieById(int id);
	
	List<Cinema> findCinemasFromMovieAndCity(Integer cityId, Integer movieId);
	
	List<Shows> findShowFromHallAndMovie(Integer hallId, Integer movieId);
	
	List<MovieShow> findMovieShowsFromShow(Integer showId);
}
