package com.demo.repositories;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.entities.Account;
import com.demo.entities.Cinema;
import com.demo.entities.City;


@Repository
public interface CityRepository extends CrudRepository<City, Integer>{
	@Query("from City ")
	public Iterable<City> findByAll();

	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
