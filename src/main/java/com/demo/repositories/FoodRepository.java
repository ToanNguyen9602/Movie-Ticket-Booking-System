package com.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import com.demo.entities.Food;

@Repository
public interface FoodRepository extends CrudRepository<Food, Integer> {
	boolean existsByName(String name);
}
