package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.entities.Cinema;
import com.demo.entities.FoodMenu;
import com.demo.entities.Movie;

import com.demo.entities.Shows;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
	@Query("from Movie where title like %:title%") 
	public List<Movie> searchMoviesByTitle(@Param("title") String title);
	
	@Query("from Movie order by id DESC")
	public List<Movie> findAll_ListMovie();
	
	@Query("SELECT m, SUM(b.price) AS totalRevenue " +
            "FROM Movie m " +
            "JOIN m.showses s " +
            "JOIN s.bookingDetailses b " +
            "GROUP BY m " +
            "ORDER BY totalRevenue DESC LIMIT 5")
    public List<Movie> findTop5MoviesByRevenue();

}
