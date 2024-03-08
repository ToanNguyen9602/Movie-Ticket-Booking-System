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
<<<<<<< HEAD
	public Iterable<Cinema> findAll() {
		// TODO Auto-generated method stub
		return cinemaRepository.findAll();
	}

	@Override
	public Cinema findCinemasById(int id) {
		// TODO Auto-generated method stub
		return cinemaRepository.findCinemasById(id);
=======
	public List<Movie> findAllMovies(Integer cinemaId) {
		return cinemaRepository.findById(cinemaId).get()
				.getShowses()
				.stream()
				.map(show -> show.getMovie())
				.toList();
>>>>>>> 045493b2e2f6feb7b60944e39cbc9d528d7029e5
	}

}
