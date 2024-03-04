package com.demo.services.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.entities.Food;
import com.demo.entities.Movie;
import com.demo.repositories.FoodRepository;
import com.demo.services.FoodService;

@Service
public class FoodServiceImpl implements FoodService {

	@Autowired
	private FoodRepository foodRepository;

	@Override
	public boolean save(Food food) {
		try {

			if (foodRepository.existsByName(food.getFoodName())) {
				
				return false;
			} else {
				
				foodRepository.save(food);
				return true;
			}

		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Iterable<Food> findAllfood() {
		// TODO Auto-generated method stub
		return foodRepository.findAll();
	}

	




}
