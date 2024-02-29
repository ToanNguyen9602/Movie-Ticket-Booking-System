package com.demo.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.demo.entities.City;
import com.demo.entities.Food;
import com.demo.entities.Movie;
import com.demo.entities.Role;




public interface FoodService {
	public boolean save(Food food);
	public Iterable<Food> findAllfood();
}
