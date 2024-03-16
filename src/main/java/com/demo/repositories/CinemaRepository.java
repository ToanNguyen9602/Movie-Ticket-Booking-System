package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.entities.Cinema;
import com.demo.entities.Hall;
import com.demo.entities.City;
import com.demo.entities.FoodMenu;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Integer> {

	boolean existsByName(String name);

	@Query("from Cinema where city.id = :cityid")
	public List<Cinema> findCinemabyCityId(@Param("cityid") int cityid);

	@Query("from Cinema where name like %:kw% ")
	public List<Cinema> SearchByCinemaName(@Param("kw") String kw);

	@Query("from Cinema order by id DESC")
	public List<Cinema> findAll_ListCinema();

}
