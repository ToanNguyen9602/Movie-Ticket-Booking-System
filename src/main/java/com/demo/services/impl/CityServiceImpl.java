package com.demo.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.demo.dtos.CityDTO;
import com.demo.entities.Blogs;
import com.demo.entities.Cinema;
import com.demo.entities.City;
import com.demo.repositories.CinemaRepository;
import com.demo.repositories.CityRepository;
import com.demo.services.CityService;

@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private CinemaRepository cinemaRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public boolean save(City city) {
		try {

			if (cityRepository.existsByName(city.getName())) {
				// Thành phố đã tồn tại trong cơ sở dữ liệu
				return false;
			} else {
				// Thêm thành phố mới vào cơ sở dữ liệu
				cityRepository.save(city);
				return true;
			}

		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<City> findAll_ListCity() {
		return cityRepository.findAll_ListCity();
	}

	@Override
	public List<Cinema> findCinemasByCityId(int city_id) {
		//return cinemaRepository.findCinemasByCityId(city_id);
		return null;
	}

	@Override
	public City findId(int id) {
		// TODO Auto-generated method stub
		return cityRepository.findById(id).get();
	}

	@Override
	public boolean delete(int id) {
		try {
			cityRepository.delete(findId(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public List<CityDTO> findAllCityDTO() {
		// TODO Auto-generated method stub
		return modelMapper.map(cityRepository.findAll(), new TypeToken<List<CityDTO>>() {
		}.getType());
	}

	@Override
	public List<City> SearchByCityName(String kw) {
		// TODO Auto-generated method stub
		return cityRepository.SearchByCityName(kw);
	}

	@Override
	public Iterable<City> findAll() {
		// TODO Auto-generated method stub
		return cityRepository.findAll();
	}

}
