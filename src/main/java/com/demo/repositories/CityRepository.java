package com.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import com.demo.entities.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
	@Query("from City ")
	public Iterable<City> findByAll();
	
	boolean existsByName(String name);

}
