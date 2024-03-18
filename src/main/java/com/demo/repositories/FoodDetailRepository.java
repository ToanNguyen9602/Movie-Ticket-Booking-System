package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.demo.entities.Account;
import com.demo.entities.Blogs;
import com.demo.entities.BookingDetails;
import com.demo.entities.City;
import com.demo.entities.FoodBookingDetails;
import com.demo.entities.Hall;

public interface FoodDetailRepository extends CrudRepository<FoodBookingDetails, Integer> {

	@Query("Select sum(price) from BookingDetails")
	public Integer IncomeFromMovie();

	@Query("Select sum(price) from BookingDetails where booking.account=:account")
	public Integer PaidforMovieByAccount(@Param("account") Account account);

}
