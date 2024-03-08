package com.demo.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.entities.Account;
import com.demo.entities.Role;
import com.demo.repositories.AccountRepository;
import com.demo.services.AccountService;

@Service
public class AccountServiceImp implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	

	@Override
	public boolean save(Account account) {
		try {
			account = accountRepository.save(account);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Account find(int id) {
		// TODO Auto-generated method stub
		return accountRepository.findById(id).get();
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		try {
			Account account = find(id);
			accountRepository.delete(account);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String getpassword(int id) {
		Account account = find(id);
		return account.getPassword();
	}

	@Override
	public Account findbyusername(String username) {
		try {
			Account account = accountRepository.findbyusername(username);
			return account;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public Account login(String username, String password) {
		try {
			return accountRepository.login(username, password, true);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public Account findbyemail(String email) {
		try {

			return accountRepository.findbyemail(email);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account= accountRepository.findbyusername(username);
		if(account==null)
		{
			throw new UsernameNotFoundException("username not found");
		}
		else
		{
			List<GrantedAuthority> authorities= new ArrayList<GrantedAuthority>();
			for(Role role: account.getRoles())
			{
				authorities.add(new SimpleGrantedAuthority(role.getName()));
			}
			return new User(username,account.getPassword(), authorities);
		}

	}

	@Override
	public boolean checkexistence(String username) {
		if(accountRepository.findbyusername(username) !=null)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean checkphone(String phone) {
		if(accountRepository.findbyphone(phone) !=null)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean checkemail(String email) {
		if(accountRepository.findbyemail(email) !=null)
		{
			return true;
		}
		return false;
	}

	@Override
	public List<Account> findAllByRole(int n) {
	    List<Account> accounts = accountRepository.findAll();
	    List<Account> accountsByRole = new ArrayList<>();
	    for (Account account : accounts) {
	        Set<Role> roles = account.getRoles();
	        for (Role role : roles) {
	            if (role.getId() == n) {
	                accountsByRole.add(account);
	            }
	        }
	    }
	    return accountsByRole;
	}



}
