package com.demo.services.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.entities.Food;
import com.demo.repositories.FoodRepository;
import com.demo.services.FoodService;

@Service
public class FoodServiceImpl implements FoodService {

	@Autowired
	private FoodRepository foodRepository;

	@Override
	public boolean save(Food food) {
		try {
			foodRepository.save(food);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
