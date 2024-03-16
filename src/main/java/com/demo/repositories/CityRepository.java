package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.entities.City;
import com.demo.entities.FoodMenu;
import com.demo.entities.Movie;

public interface CityRepository extends JpaRepository<City, Integer> {
	
	boolean existsByName(String name);
	
	@Query("from City where name like %:kw% ")
	public List<City> SearchByCityName(@Param("kw") String kw);
	
	@Query("from City order by id DESC")
	public List<City> findAll_ListCity();

}
