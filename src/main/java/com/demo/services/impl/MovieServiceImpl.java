package com.demo.services.impl;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.Cinema;
import com.demo.entities.Hall;
import com.demo.entities.Movie;
import com.demo.entities.MovieShow;
import com.demo.entities.Shows;
import com.demo.repositories.CinemaRepository;
import com.demo.repositories.MovieRepository;
import com.demo.services.MovieService;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private CinemaRepository cinemaRepository;
	
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


	@Override
	public Movie findMovieById(int id) {
		return movieRepository.findById(id).get();
	}


	@Override
	public List<Cinema> findCinemasFromMovieAndCity(Integer cityId, Integer movieId) {
		List<Cinema> cinemas = cinemaRepository.findAll()
				.stream()
				.filter(cinema -> cinema.getCity().getId() == cityId)
				.filter(cinema -> {
					var shows = cinema.getShowses().stream()
						.filter(show -> show.getMovie().getId() == movieId)
						.limit(1)
						.toList();
					return shows != null && shows.size() > 0; 
				})
				.toList();
		return cinemas;
	}
	
	@Override
	public List<Shows> findShowFromHallAndMovie(Integer hallId, Integer movieId) {
		var shows = movieRepository.findAll()
				.stream();
		return null;
	}


	@Override
	public List<MovieShow> findMovieShowsFromShow(Integer showId) {
		// TODO Auto-generated method stub
		return null;
	}


	


	

}
