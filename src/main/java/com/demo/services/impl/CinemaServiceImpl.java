package com.demo.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.demo.dtos.CinemaDTO;
import com.demo.dtos.CityDTO;
import com.demo.entities.Cinema;
import com.demo.entities.City;
import com.demo.entities.Hall;
import com.demo.entities.Movie;
import com.demo.repositories.CinemaRepository;
import com.demo.repositories.HallRepository;
import com.demo.services.CinemaService;

@Service
public class CinemaServiceImpl implements CinemaService {

	@Autowired
	private CinemaRepository cinemaRepository;

	@Autowired
	private HallRepository hallRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public boolean save(Cinema cinema) {
		try {
			cinemaRepository.save(cinema);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Cinema> findAll() {
		return cinemaRepository.findAll();
	}

	@Override
	public Cinema findById(int id) {
		return cinemaRepository.findById(id).get();
	}

	@Override
	public List<Cinema> findCinemasFromCityAndMovie(Integer cityId, Integer movieId) {
		List<Cinema> cinemas = cinemaRepository.findAll().stream().filter(cinema -> cinema.getCity().getId() == cityId)
				.filter(cinema -> {
					var shows = cinema.getShowses().stream().filter(show -> show.getMovie().getId() == movieId).limit(1)
							.toList();
					return shows != null && shows.size() > 0;
				}).toList();
		return cinemas;
	}

	@Override
	public List<Hall> findHallsByCinemaId(int cinemaid) {
		// TODO Auto-generated method stub
		return hallRepository.findHallsByCinemaId(cinemaid);
	}

	@Override
	public List<Cinema> findCinemaByCityId(int city_id) {
		var cinema = new Cinema();
		var city = new City(city_id);
		cinema.setCity(city);

		return cinemaRepository.findAll(Example.of(cinema));
	}

	@Override
	public boolean delete(int id) {
		try {
			cinemaRepository.delete(findById(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<CinemaDTO> findallCinemabyCityid(int cityid) {
		// TODO Auto-generated method stub
		return modelMapper.map(cinemaRepository.findCinemabyCityId(cityid), new TypeToken<List<CinemaDTO>>() {
		}.getType());
	}


}
