package com.demo.services;

import java.util.List;


import com.demo.entities.FoodMenu;





public interface FoodService {
	public boolean save(FoodMenu food);
	public boolean save2(FoodMenu food);
	public List<FoodMenu> findAll_ListFood();
	public FoodMenu find(int id);
	
}
