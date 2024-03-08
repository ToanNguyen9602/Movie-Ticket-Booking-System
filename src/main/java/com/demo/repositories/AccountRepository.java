package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
	@Query("from Account where username=:username")
	public Account findbyusername(@Param("username") String username);
	
	@Query("from Account where username=:username and password=:password and status=:status")
	public Account login(@Param("username") String username,@Param("password") String password,@Param("status") boolean status);
	
	@Query("from Account where email=:email")
	public Account findbyemail(@Param("email") String email);
	
	@Query("from Account where id=:id")
	public Account findAccountById(@Param("id") int id);
	
	@Query("from Account where phone=:phone")
	public Account findbyphone(@Param("phone") String phone);
	

}
