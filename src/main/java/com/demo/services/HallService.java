package com.demo.services;

import java.util.List;

import com.demo.entities.Blogs;
import com.demo.entities.Hall;



public interface HallService {

	public List<Hall> findHallByCinemaId(int cinemaid);
	
	

}
