package com.demo.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.demo.entities.FoodMenu;






public interface FoodService {
	public boolean save(FoodMenu food);
	public boolean save2(FoodMenu food);
	public List<FoodMenu> findAll_ListFood();
	public Page<FoodMenu> findAll_ListFoodpagin(int pageNo, int pageSize);
	public FoodMenu find(int id);
	public List<FoodMenu> SearchByFoodName1(String kw);
	public Page<FoodMenu> SearchByFoodName(String kw,int pageNo, int pageSize);
	
}
