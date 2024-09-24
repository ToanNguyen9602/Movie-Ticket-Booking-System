package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.demo.entities.Account;
import com.demo.entities.Blogs;
import com.demo.entities.BookingDetails;
import com.demo.entities.City;
import com.demo.entities.Hall;
import org.springframework.transaction.annotation.Transactional;

public interface BookingDetailRepository extends JpaRepository<BookingDetails, Integer> {

	@Modifying
	@Transactional
	@Query(value = "insert into booking_details(price,booking_id,seats_id,shows_id) values(:price,:bk_id,:st_id,:sh_id)" , nativeQuery = true)
	public void saveS(Double price,int bk_id,int st_id ,int sh_id);

	@Query("Select COALESCE(sum(price)) from BookingDetails")
	public Integer IncomeFromMovie();

	@Query("Select COALESCE(sum(price)) from BookingDetails where booking.account=:account")
	public Integer PaidforMovieByAccount(@Param("account") Account account);

	@Query("SELECT COALESCE(SUM(b.price)) " + "FROM BookingDetails b " + "WHERE b.shows.movie.id = :movieId")
	public Integer sumPriceByMovieId(Integer movieId);

	@Query("SELECT COALESCE(SUM(b.price), 0) " + "FROM BookingDetails b " + "WHERE b.shows.movie.id = :movieId")
	public Integer sumOfPricesByMovieId(int movieId);


}
