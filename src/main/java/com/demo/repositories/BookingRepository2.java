package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.demo.entities.Blogs;
import com.demo.entities.Booking;
import com.demo.entities.City;
import com.demo.entities.Hall;

public interface BookingRepository2 extends CrudRepository<Booking, Integer> {

	@Query("from Booking where account.id = :accountId")
	public List<Booking> findBookingbyAccountId(@Param("accountId") int accountId);

	@Query("from Booking where id=:bookingId")
	public Booking findBookingbyBookingId(@Param("bookingId") int bookingId);

}
