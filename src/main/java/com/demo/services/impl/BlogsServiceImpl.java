package com.demo.services.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.demo.dtos.BookingDTO;
import com.demo.dtos.FoodBookingDTO;
import com.demo.entities.Account;
import com.demo.entities.Blogs;
import com.demo.entities.Booking;
import com.demo.entities.BookingDetails;
import com.demo.entities.FoodBookingDetails;
import com.demo.entities.FoodMenu;
import com.demo.repositories.BlogsRepository;
import com.demo.services.BlogsService;

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

	@Override
	public List<Blogs> findByAllonAdminPage() {
		// TODO Auto-generated method stub
		return blogsRepository.findByAllonAdminPage();
	}

	public boolean delete(int id) {
		try {
			Blogs blog = findByIdonAdminPage(id);
			blogsRepository.delete(blog);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Blogs findByIdonAdminPage(int id) {
		return blogsRepository.findByIdonAdminPage(id);
	}

	@Override
	public List<Blogs> searchblogs(String title) {
		// TODO Auto-generated method stub
		return blogsRepository.findallbytitle(title);
	}

	

}
