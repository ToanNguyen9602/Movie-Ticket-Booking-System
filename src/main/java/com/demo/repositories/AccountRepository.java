package com.demo.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.entities.Account;
import com.demo.entities.FoodMenu;

public interface AccountRepository extends CrudRepository<Account, Integer> {
	@Query("from Account where username=:username")
	public Account findbyusername(@Param("username") String username);

	@Query("from Account where username=:username and password=:password and status=:status")
	public Account login(@Param("username") String username, @Param("password") String password,
			@Param("status") boolean status);

	@Query("from Account where email=:email")
	public Account findbyemail(@Param("email") String email);

	@Query("from Account where id=:id")
	public Account findAccountById(@Param("id") int id);

	@Query("from Account where phone=:phone")
	public Account findbyphone(@Param("phone") String phone);

	@Query("from Account order by id desc")
	public Page<Account> findAll(Pageable pageable);

	@Query("SELECT a FROM Account a JOIN a.roles r WHERE a.username LIKE %:username% AND r.id = :id")
	public List<Account> searchAccounts(@Param("username") String username, @Param("id") int id);

	@Query("SELECT COALESCE(SUM(bd.price)) FROM Account a JOIN a.bookings b JOIN b.bookingDetailses bd WHERE a.id = :accountId")
	public Integer sumBookingPricesByAccountId(@Param("accountId") Integer accountId);

	@Query("SELECT COALESCE(SUM(bd.price*bd.quantity))FROM Account a JOIN a.bookings b JOIN b.foodBookingDetailses bd WHERE a.id = :accountId")
	public Integer sumFoodPricesByAccountId(@Param("accountId") Integer accountId);

	@Query("SELECT COUNT(a) FROM Account a JOIN a.roles r WHERE r.id = ?1")
	public Integer countAccountsWithRoleId(Integer roleId);

	@Query("SELECT a, COALESCE(SUM(bd.price),0) + COALESCE(SUM(fbd.price*fbd.quantity),0) AS totalSum "
			+ "FROM Account a " + "LEFT JOIN a.bookings b " + "LEFT JOIN b.bookingDetailses bd "
			+ "LEFT JOIN b.foodBookingDetailses fbd " + "GROUP BY a " + "ORDER BY totalSum DESC LIMIT 10")
	public List<Account> findTop5AccountsByTotalPriceWithLimit();

	@Query("SELECT COALESCE(SUM(bd.price),0) + COALESCE(SUM(fbd.price*fbd.quantity),0) " + "FROM BookingDetails bd "
			+ "JOIN bd.booking b " + "JOIN FoodBookingDetails fbd " + "ON bd.id.bookingId = fbd.id.bookingId "
			+ "WHERE b.account.id = :accountId order by COALESCE(SUM(bd.price)) + COALESCE(SUM(fbd.price*fbd.quantity))")
	public Integer getTotalPriceByAccountId(Integer accountId);

	@Query("from Account order by id desc")
	public List<Account> findallaccount();

}
