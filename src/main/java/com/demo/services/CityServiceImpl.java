package com.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.entities.City;
import com.demo.entities.Movie;
import com.demo.repositories.AccountRepository;
import com.demo.repositories.CityRepository;
import com.demo.repositories.MovieRepository;

@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private CityRepository cityRepository;

	@Override
	public boolean save(City city) {
		try {
			cityRepository.save(city);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
