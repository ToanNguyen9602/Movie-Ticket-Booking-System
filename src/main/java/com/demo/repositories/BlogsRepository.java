package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.demo.entities.Blogs;
import com.demo.entities.City;

public interface BlogsRepository  extends CrudRepository<Blogs, Integer>{
	
	@Query("from Blogs where status=true order by id DESC ")
	public List<Blogs> findByAll();
	
	@Query("from Blogs where id=:id and status=true")
	public Blogs findById(@Param("id") int id);

}
