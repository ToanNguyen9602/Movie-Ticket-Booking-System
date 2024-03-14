package com.demo.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.demo.entities.Blogs;
import com.demo.entities.Cinema;
import com.demo.entities.City;
import com.demo.repositories.BlogsRepository;
import com.demo.repositories.CinemaRepository;
import com.demo.repositories.CityRepository;
import com.demo.services.BlogsService;
import com.demo.services.CityService;

@Service
public class BlogsServiceImpl implements BlogsService {

	@Autowired
	private BlogsRepository blogsRepository;

	@Override
	public boolean save(Blogs blogs) {
		try {
			blogsRepository.save(blogs);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Blogs> findByAll() {
		// TODO Auto-generated method stub
		return blogsRepository.findByAll();
	}

	@Override
	public Blogs findById(int id) {
		return blogsRepository.findById(id);
	}

}
