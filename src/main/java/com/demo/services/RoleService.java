package com.demo.services;


import org.springframework.stereotype.Service;

import com.demo.entities.Role;


public interface RoleService {

	public Iterable<Role> findAll();
	public Role findrolebyid (int id); 
	
}
