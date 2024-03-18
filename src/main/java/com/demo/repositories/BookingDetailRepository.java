package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.demo.entities.Account;
import com.demo.entities.Blogs;
import com.demo.entities.BookingDetails;
import com.demo.entities.City;
import com.demo.entities.Hall;

public interface BookingDetailRepository extends CrudRepository<BookingDetails, Integer> {

	@Query("Select sum(price) from BookingDetails")
	public Integer IncomeFromMovie();

	@Query("Select sum(price) from BookingDetails where booking.account=:account")
	public Integer PaidforMovieByAccount(@Param("account") Account account);

	@Query("SELECT SUM(b.price) " + "FROM BookingDetails b " + "WHERE b.shows.movie.id = :movieId")
	public Integer sumPriceByMovieId(Integer movieId);

	@Query("SELECT COALESCE(SUM(b.price), 0) " + "FROM BookingDetails b " + "WHERE b.shows.movie.id = :movieId")
	public Integer sumOfPricesByMovieId(int movieId);

}
