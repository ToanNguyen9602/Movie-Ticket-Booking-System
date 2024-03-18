package com.demo.services;

import java.util.Date;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.demo.entities.Movie;

import com.demo.entities.Shows;
import com.demo.enums.MovieStatus;

public interface MovieService {
	boolean save(Movie movie);

	public List<Movie> findAll_ListMovie();
	
	public List<Movie> findAll();
	
	List<Movie> findAll(Integer cinemaId);

	List<Movie> findAll(@Nullable Integer cinemaId, MovieStatus STATUS);

	List<Movie> searchMoviesByTitle(String title);
	
	List<Movie> searchMoviesByTitle1(String title);
	
	Movie findMovieById(Integer id);
	
	public boolean delete(int id);
	
	List<Shows> findShowsFromCinemaAndMovie(@NonNull Integer cinemaId, @NonNull Integer movieId, Date date);

	List<Date> findDatesFromCinemaAndMovieUntilNoutFoundFromNow(@NonNull Integer cinemaId, @NonNull Integer movieId);
	
	public boolean isMovieShowingNow(Integer movieId);
	public List<Movie> top5Movies();
	
	
}
