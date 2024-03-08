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
			if (cinemaRepository.existsByName(cinema.getName())) return false;
			
			cinemaRepository.save(cinema);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Cinema> findAll() {
		return cinemaRepository.findAll();
	}

	@Override
	public Cinema findCinemasById(int id) {
		return cinemaRepository.findById(id).get();
	}

	@Override
	public List<Cinema> findCinemasFromCityAndMovie(Integer cityId, Integer movieId) {
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

}
