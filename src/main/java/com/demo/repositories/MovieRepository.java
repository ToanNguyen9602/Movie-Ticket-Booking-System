package com.demo.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.demo.entities.Movie;



@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
	
	boolean existsByTitle(String title);
	
	@Query("from Movie where title like %:title%")
	public List<Movie> searchMoviesByTitle(@Param("title") String title);

	@Query("from Movie order by id DESC")
	public List<Movie> findAll_ListMovie();

	@Query("SELECT m FROM Movie m WHERE m.releaseDate <= :currentDate AND (m.endDate IS NULL OR m.endDate >= :currentDate) order by id DESC")
	Page<Movie> findNowShowingMovies(@Param("currentDate") Date currentDate,Pageable pageable);

	@Query("SELECT m FROM Movie m WHERE m.releaseDate > :currentDate order by id DESC")
	Page<Movie> findUpcomingMovies(@Param("currentDate") Date currentDate, Pageable pageable);

	@Query("SELECT m FROM Movie m WHERE m.releaseDate <= :currentDate AND (m.endDate IS NULL OR m.endDate >= :currentDate) AND (LOWER(m.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(m.genre) LIKE LOWER(CONCAT('%', :keyword, '%'))) order by id DESC")
	List<Movie> searchNowShowingMovies(@Param("keyword") String keyword, @Param("currentDate") Date currentDate);

	@Query("SELECT m FROM Movie m WHERE m.releaseDate > :currentDate AND (LOWER(m.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(m.genre) LIKE LOWER(CONCAT('%', :keyword, '%'))) order by id DESC")
	List<Movie> searchUpcomingMovies(@Param("keyword") String keyword, @Param("currentDate") Date currentDate);
	
	@Query("SELECT m, SUM(b.price) AS totalRevenue " +
            "FROM Movie m " +
            "JOIN m.showses s " +
            "JOIN s.bookingDetailses b " +
            "GROUP BY m " +
            "ORDER BY totalRevenue DESC LIMIT 10")
    public List<Movie> findTop5MoviesByRevenue();


}
