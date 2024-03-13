package com.demo.services;

import java.util.List;

import com.demo.entities.Blogs;
import com.demo.entities.Hall;



public interface HallService {

	public List<Hall> findHallsByCinemaId(int cinemaid);

	public boolean save(Hall hall);
	
	public Hall findHallbyId(int id);
	
	public Integer saveAndGetId(Hall hall);
	
	

}
