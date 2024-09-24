package com.demo.services.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.demo.entities.Cinema;
import com.demo.entities.FoodMenu;
import com.demo.entities.Movie;
import com.demo.entities.Shows;
import com.demo.enums.MovieStatus;

import static com.demo.helpers.DateHelper.*;
import com.demo.repositories.CinemaRepository;
import com.demo.repositories.MovieRepository;
import com.demo.repositories.ShowsRepository;
import com.demo.services.MovieService;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private CinemaRepository cinemaRepository;

	@Autowired
	private ShowsRepository showsRepository;

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
		return findAllMoviesBeingShown(movieRepository.searchMoviesByTitle(title));
	}

	@Override
	public Movie findMovieById(Integer id) {
		return movieRepository.findById(id).get();
	}

	@Override
	public List<Shows> findShowsFromCinemaAndMovie(@NonNull Integer cinemaId, @NonNull Integer movieId, Date date) {
		List<Shows> shows = getShowsByCinemaAndMovie(new Cinema(cinemaId), new Movie(movieId));
		if (date != null) {
			LocalDate comparisonDate = mapFromDate(date);
			shows = shows.stream()
					.filter(show -> {
						LocalDate showDate = mapFromDate(show.getStartTime());
						return showDate.compareTo(comparisonDate) == 0;
					})
					.filter(show -> {
						return show.getStartTime().compareTo(new Date()) > 0;
					})
					.collect(Collectors.toList());
		}
		shows.sort((o1, o2) -> o1.getStartTime().compareTo(o2.getStartTime()));
		return shows;
	}

	private List<Shows> getShowsByCinemaAndMovie(Cinema cinema, Movie movie) {
		Shows exampleShow = new Shows();
		exampleShow.setCinema(cinema);
		exampleShow.setMovie(movie);
		return showsRepository.findAll(Example.of(exampleShow));
	}

	@Override
	public List<Movie> findAll(Integer cinemaId) {
		return movieRepository.findAll().stream().filter(movie -> {
			var filteredMovie = movie.getShowses().stream().filter(show -> show.getCinema().getId() == cinemaId)
					.toList();
			return filteredMovie.size() > 0;
		}).toList();
	}

	@Override
	public List<Movie> findAll(@Nullable Integer cinemaId, MovieStatus STATUS) {
		var filteredMoviesByCinemaId = cinemaId == null ? findAll() : findAll(cinemaId);
		List<Movie> movies;
		switch (STATUS) {
		case ABOUT_TO_BE_SHOWN -> {
			movies = findAllMoviesAboutToBeShown(filteredMoviesByCinemaId);
		}
		case BEING_SHOWN -> {
			movies = findAllMoviesBeingShown(filteredMoviesByCinemaId);
		}
		case SHOWN -> {
			movies = findAllMoviesShown(filteredMoviesByCinemaId);
		}
		default -> {
			movies = filteredMoviesByCinemaId;
		}
		}
		return movies;
	}

	private List<Movie> findAllMoviesAboutToBeShown(List<Movie> movies) {
		return movies.stream().filter(movie -> movie.getReleaseDate().after(new Date())).toList();
	}

	private List<Movie> findAllMoviesBeingShown(List<Movie> movies) {
		return movies.stream().filter(movie -> {
			var currentDate = new Date();
			return movie.getReleaseDate().before(currentDate) && movie.getEndDate().after(currentDate);
		}).toList();
	}

	private List<Movie> findAllMoviesShown(List<Movie> movies) {
		return movies.stream().filter(movie -> movie.getEndDate().before(new Date())).toList();
	}

	@Override
	public List<Date> findDatesFromCinemaAndMovieUntilNoutFoundFromNow(Integer cinemaId, Integer movieId) {
		List<Shows> shows = getShowsByCinemaAndMovie(new Cinema(cinemaId), new Movie(movieId));
		shows.sort((s1, s2) -> s2.getStartTime().compareTo(s1.getStartTime()));

		List<Date> datesFromNowToLastShow = new ArrayList<>();
		if (shows.size() > 0) {
			Date lastShowDate = shows.get(0).getStartTime();
			datesFromNowToLastShow = getDatesBetween(new Date(), lastShowDate);
		}
		return datesFromNowToLastShow;
	}

	@Override
	public boolean isMovieShowingNow(Integer movieId) {
		Movie movie = findMovieById(movieId);
		var currentDate = new Date();
		return movie.getReleaseDate().before(currentDate) && movie.getEndDate().after(currentDate);
	}

	@Override
	public boolean delete(int id) {
		try {
			movieRepository.delete(findMovieById(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Movie> findAll() {
		// TODO Auto-generated method stub
		return movieRepository.findAll();
	}

	@Override

	public Page<Movie> findNowShowingMovies(int pageNo, int pageSize) {
		Date currentDate = new Date();
		Pageable pageable = PageRequest.of(pageNo -1 , pageSize);
		return movieRepository.findNowShowingMovies(currentDate,pageable);
	}

	@Override
	public Page<Movie> findUpcomingMovies(int pageNo, int pageSize) {
		Date currentDate = new Date();
		Pageable pageable = PageRequest.of(pageNo -1 , pageSize);
		return movieRepository.findUpcomingMovies(currentDate,pageable);
	}

	@Override
	public List<Movie> searchNowShowingMovies1(String keyword) {
		Date currentDate = new Date();
		return movieRepository.searchNowShowingMovies(keyword, currentDate);
	}

	@Override
	public List<Movie> searchUpcomingMovies1(String keyword) {
		Date currentDate = new Date();
		return movieRepository.searchUpcomingMovies(keyword, currentDate);
	}

	@Override
	public Page<Movie> searchNowShowingMovies(String keyword, int pageNo, int pageSize) {
		List list = this.searchNowShowingMovies1(keyword);
		
		Pageable pageable = PageRequest.of(pageNo -1 , pageSize);
		
		Integer start = (int) pageable.getOffset();
		Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size() : pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<Movie>(list, pageable, this.searchNowShowingMovies1(keyword).size());
	}

	@Override
	public Page<Movie> searchUpcomingMovies(String keyword, int pageNo, int pageSize) {
		List list = this.searchUpcomingMovies1(keyword);
		
		Pageable pageable = PageRequest.of(pageNo -1 , pageSize);
		
		Integer start = (int) pageable.getOffset();
		Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size() : pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<Movie>(list, pageable, this.searchUpcomingMovies1(keyword).size());
	}

	@Override
	public boolean save2(Movie movie) {
		try {

			if (movieRepository.existsByTitle(movie.getTitle())) {
				return false;
			} else {
				movieRepository.save(movie);
				return true;
			}

		} catch (Exception e) {
			return false;
		}
	}


	public List<Movie> top5Movies() {
		// TODO Auto-generated method stub
		return movieRepository.findTop5MoviesByRevenue();
	}

	

}
