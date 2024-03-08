package com.demo.services;

import java.util.List;

import com.demo.entities.Blogs;



public interface BlogsService {
	public boolean save(Blogs blogs );

	public List<Blogs> findByAll();

	
	

}
