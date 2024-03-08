package com.demo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.Role;
import com.demo.repositories.RoleRepository;
import com.demo.services.RoleService;

@Service
public class RoleServiceImp implements RoleService {
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Iterable<Role> findAll() {
		// TODO Auto-generated method stub
		return roleRepository.findAll();
	}

	@Override
	public Role findrolebyid(int id) {
		// TODO Auto-generated method stub
		return roleRepository.findrolebyid(id);
	}

}
