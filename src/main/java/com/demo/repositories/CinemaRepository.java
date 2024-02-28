package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.entities.Account;
import com.demo.entities.Cinema;
import com.demo.entities.Food;
import com.demo.entities.Movie;




@Repository
public interface CinemaRepository extends CrudRepository<Cinema, Integer> {


	
}
