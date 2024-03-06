package com.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.demo.entities.Account;

public interface AccountService extends UserDetailsService {

	public Iterable<Account> findAll();

	public Account find(int id);

	public boolean save(Account account);

	public boolean delete(int id);

	public String getpassword(int id);

	public Account findbyusername(String username);

	public Account login(String username, String password);

	public Account findbyemail(String email);

}
