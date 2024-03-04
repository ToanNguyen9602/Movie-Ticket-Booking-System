package com.demo.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.demo.entities.Cinema;
import com.demo.entities.Movie;
import com.demo.entities.Role;




public interface MovieService {
	boolean save(Movie movie);

	List<Movie> findAllMovie();
	
	List<Movie> searchMoviesByTitle(String title);
	
	public List<Movie> findMovieById(int id);
}
