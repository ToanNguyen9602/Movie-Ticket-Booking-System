package com.demo.services;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.demo.entities.Movie;

import com.demo.entities.Shows;
import com.demo.enums.MovieStatus;

public interface MovieService {
	boolean save(Movie movie);
	
	boolean save2(Movie movie);
	Page<Movie> findNowShowingMovies(int pageNo, int pageSize);

	Page<Movie> findUpcomingMovies(int pageNo, int pageSize);

	public List<Movie> findAll();

	List<Movie> findAll(Integer cinemaId);

	List<Movie> findAll(@Nullable Integer cinemaId, MovieStatus STATUS);

	List<Movie> searchMoviesByTitle(String title);

	List<Movie> searchNowShowingMovies1(String keyword);
	
	Page<Movie> searchNowShowingMovies(String keyword,int pageNo, int pageSize);

	List<Movie> searchUpcomingMovies1(String keyword);
	
	Page<Movie> searchUpcomingMovies(String keyword,int pageNo, int pageSize);

	Movie findMovieById(Integer id);

	public boolean delete(int id);

	List<Shows> findShowsFromCinemaAndMovie(@NonNull Integer cinemaId, @NonNull Integer movieId, Date date);

	List<Date> findDatesFromCinemaAndMovieUntilNoutFoundFromNow(@NonNull Integer cinemaId, @NonNull Integer movieId);




	
	public boolean isMovieShowingNow(Integer movieId);
	
	public List<Movie> top5Movies();
	
	
}
