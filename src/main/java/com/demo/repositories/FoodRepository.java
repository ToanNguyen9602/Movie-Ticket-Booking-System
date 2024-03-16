package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.entities.Blogs;
import com.demo.entities.FoodMenu;

@Repository
public interface FoodRepository extends JpaRepository<FoodMenu, Integer> {
	boolean existsByName(String name);
	
	@Query("from FoodMenu where status = true order by id DESC")
	public List<FoodMenu> findAll_ListFood();
	
	@Query("from FoodMenu where name like %:kw% ")
	public List<FoodMenu> SearchByFoodName(@Param("kw") String kw);
}
