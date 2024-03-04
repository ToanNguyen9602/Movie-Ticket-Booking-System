package com.demo.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.demo.entities.City;
import com.demo.entities.FoodMenu;
import com.demo.entities.Movie;
import com.demo.entities.Role;




public interface FoodService {
	public boolean save(FoodMenu food);
	public Iterable<FoodMenu> findAllfood();
}
