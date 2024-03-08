package com.demo.services.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.demo.MovieStatus;
import com.demo.entities.Cinema;
import com.demo.entities.Movie;
import com.demo.entities.Shows;
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
		return movieRepository.searchMoviesByTitle(title);
	}

	@Override
	public Movie findMovieById(int id) {
		return movieRepository.findById(id).get();
	}

	@Override
	public List<Shows> findShowsFromCinemaAndMovie(@NonNull Integer cinemaId, @NonNull Integer movieId, Date date) {
		Shows exampleShows = new Shows();
		exampleShows.setMovie(new Movie(movieId));
		exampleShows.setCinema(new Cinema(cinemaId));		
		List<Shows> shows = showsRepository.findAll(Example.of(exampleShows)); 
		if (date != null) {
            shows = shows.stream()
                .filter(show -> {
                    LocalDate showDate = show.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate comparisonDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    return showDate.equals(comparisonDate);
                })
                .toList();
        }
        return shows;
	}

	@Override
	public List<Movie> findAll() {
		return movieRepository.findAll();
	}

	@Override
	public List<Movie> findAll(Integer cinemaId) {
		return movieRepository.findAll()
				.stream()
				.filter(movie -> {
					var filteredMovie = movie.getShowses()
						.stream()
						.filter(show -> show.getCinema().getId() == cinemaId)
						.toList();
					return filteredMovie.size() > 0;
				})
				.toList();
	}

	@Override
	public List<Movie> findAll(Integer cinemaId, MovieStatus STATUS) {
		var filteredMoviesByCinemaId = findAll(cinemaId); 
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
		return movies.stream()
				.filter(movie -> movie.getReleaseDate().after(new Date()))
				.toList();
	}
	
	private List<Movie> findAllMoviesBeingShown(List<Movie> movies) {
		return movies.stream()
				.filter(movie -> {
					var currentDate = new Date();
					return movie.getReleaseDate().after(currentDate) && movie.getEndDate().before(currentDate);
				})
				.toList();
	}
	
	private List<Movie> findAllMoviesShown(List<Movie> movies) {
		return movies.stream()
				.filter(movie -> movie.getEndDate().before(new Date()))
				.toList();
	}

	
}
