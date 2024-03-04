package com.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import com.demo.entities.FoodMenu;

@Repository
public interface FoodRepository extends CrudRepository<FoodMenu, Integer> {
	boolean existsByName(String name);
}
