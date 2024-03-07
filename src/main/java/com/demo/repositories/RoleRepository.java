package com.demo.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.demo.entities.Account;
import com.demo.entities.Role;






public interface RoleRepository  extends CrudRepository<Role, Integer>{

	@Query("from Role where id=:id")
	public Role findrolebyid(@Param("id") int id);
}
