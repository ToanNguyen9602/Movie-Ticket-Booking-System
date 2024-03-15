package com.demo.services.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.entities.FoodMenu;
import com.demo.entities.Movie;
import com.demo.repositories.FoodRepository;
import com.demo.services.FoodService;

@Service
public class FoodServiceImpl implements FoodService {

	@Autowired
	private FoodRepository foodRepository;

	@Override
	public boolean save(FoodMenu food) {
		try {

			if (foodRepository.existsByName(food.getName())) {
				
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
	public List<FoodMenu> findAll_ListFood() {
		// TODO Auto-generated method stub
		return foodRepository.findAll_ListFood();
	}

	@Override
	public FoodMenu find(int id) {
		// TODO Auto-generated method stub
		return foodRepository.findById(id).get();
	}
	
	@Override
	public boolean save2(FoodMenu food) {
		try {
				
				foodRepository.save(food);
				return true;
			}

		 catch (Exception e) {
			return false;
		}
	}

	

	

	




}
