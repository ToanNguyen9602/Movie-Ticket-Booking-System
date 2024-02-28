package com.demo.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;



public interface AccountService extends UserDetailsService {

	
	
	public String getpassword(String username);
}
