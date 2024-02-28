package com.demo.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.demo.entities.City;
import com.demo.entities.Movie;
import com.demo.entities.Role;




public interface CityService {
	public boolean save(City city);
	
}
