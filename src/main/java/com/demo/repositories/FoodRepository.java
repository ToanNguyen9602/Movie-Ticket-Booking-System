package com.demo.repositories;

<<<<<<< HEAD
import java.util.List;

import org.springframework.data.jpa.repository.Query;
=======
import org.springframework.data.jpa.repository.JpaRepository;
>>>>>>> 045493b2e2f6feb7b60944e39cbc9d528d7029e5
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import com.demo.entities.FoodMenu;

@Repository
public interface FoodRepository extends JpaRepository<FoodMenu, Integer> {
	boolean existsByName(String name);
	
	@Query("from FoodMenu where status = true")
	public List<FoodMenu> findAll_ListFood();
}
