package com.demo.services.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.entities.Cinema;
import com.demo.entities.Movie;
import com.demo.repositories.CinemaRepository;
import com.demo.services.CinemaService;

@Service
public class CinemaServiceImpl implements CinemaService {

	@Autowired
	private CinemaRepository cinemaRepository;

	@Override
	public boolean save(Cinema cinema) {
		try {

			if (cinemaRepository.existsByName(cinema.getName())) {
				
				return false;
			} else {
				
				cinemaRepository.save(cinema);
				return true;
			}

		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Movie> findAllMovies(Integer cinemaId) {
		return cinemaRepository.findById(cinemaId).get()
				.getShowses()
				.stream()
				.map(show -> show.getMovie())
				.toList();
	}

}
