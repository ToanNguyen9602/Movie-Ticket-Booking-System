package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.entities.Cinema;
import com.demo.entities.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
	@Query("from Movie where title like %:title%") 
	public List<Movie> searchMoviesByTitle(@Param("title") String title); 
	
	@Query("from Movie where id = :id")
	public List<Movie> findMovieById(@Param("id") int id);
}
