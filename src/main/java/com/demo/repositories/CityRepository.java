package com.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import com.demo.entities.City;

@Repository
<<<<<<< HEAD
public interface CityRepository extends CrudRepository<City, Integer> {
	
=======
public interface CityRepository extends JpaRepository<City, Integer> {
>>>>>>> 045493b2e2f6feb7b60944e39cbc9d528d7029e5
	@Query("from City ")
	public Iterable<City> findByAll();
	
	boolean existsByName(String name);

}
