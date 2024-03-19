package com.demo.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.demo.entities.Account;

public interface AccountService extends UserDetailsService {

	public Account find(int id);

	public boolean save(Account account);

	public boolean delete(int id);

	public String getpassword(int id);

	public Account findbyusername(String username);

	public Account login(String username, String password);

	public Account findbyemail(String email);

	public boolean checkexistence(String username);

	public boolean checkphone(String phone);

	public boolean checkemail(String email);

	public Page<Account> findAllByRole(int n, int pageNo, int pageSize);

	public List<Account> findAllByRoles(int n);

	public String getpassword(String username);

	public boolean isLoggedIn();

	public List<Account> findAccount1(String kw, int id);

	//public Page<Account> findAccount(String kw, int id, int pageNo, int pageSize);

	public List<Account> findAccount(String kw, int id);

	public Integer paidForMoviebyAccountId(int id);

	public Integer sumFoodPricesByAccountId(int id);

	public Integer countAccountsWithRoleId(int id);

	public List<Account> top5paid();

	public Integer allPaidbyAccountId(int accountId);

}
