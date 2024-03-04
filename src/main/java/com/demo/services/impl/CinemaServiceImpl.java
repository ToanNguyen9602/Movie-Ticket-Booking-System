package com.demo.services.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.entities.Cinema;
import com.demo.repositories.CinemaRepository;
import com.demo.services.CinemaService;

@Service
public class CinemaServiceImpl implements CinemaService {

	@Autowired
	private CinemaRepository cinemaRepository;

	@Override
	public boolean save(Cinema cinema) {
		try {

			if (cinemaRepository.existsByName(cinema.getCineName())) {
				
				return false;
			} else {
				
				cinemaRepository.save(cinema);
				return true;
			}

		} catch (Exception e) {
			return false;
		}
	}

}
