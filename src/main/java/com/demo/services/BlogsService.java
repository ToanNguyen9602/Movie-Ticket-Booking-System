package com.demo.services;

import java.util.List;

import com.demo.dtos.BookingDTO;
import com.demo.entities.Blogs;

public interface BlogsService {
	public boolean save(Blogs blogs);

	public List<Blogs> findByAll();

	public Blogs findById(int id);

	public List<Blogs> findByAllonAdminPage();
	public Blogs findByIdonAdminPage(int id);
	public boolean delete(int id);
	
	public List<Blogs> searchblogs(String title);

}
