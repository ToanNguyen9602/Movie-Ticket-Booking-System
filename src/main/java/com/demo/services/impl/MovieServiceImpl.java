package com.demo.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.entities.Movie;
import com.demo.repositories.AccountRepository;
import com.demo.repositories.MovieRepository;
import com.demo.services.MovieService;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieRepository movieRepository;

	
	@Override
	public boolean save(Movie movie) { 
		try {
			movieRepository.save(movie);
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	@Override
	public List<Movie> searchMoviesByTitle(String title) {
		return movieRepository.searchMoviesByTitle(title);
	}

	@Override
	public List<Movie> findAllMovie() {
		return movieRepository.findAll();
	}

}
