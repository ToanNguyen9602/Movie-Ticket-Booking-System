package com.demo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.Role;
import com.demo.repositories.RoleRepository;
import com.demo.services.RoleService;

@Service
public class RoleServiceImp implements RoleService {
	@Autowired
	private RoleRepository repository;

	@Override
	public Iterable<Role> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

}
