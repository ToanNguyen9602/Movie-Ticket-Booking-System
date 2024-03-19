package com.demo.services;

import java.util.List;
import java.util.Map;

import com.demo.dtos.HallDTO;
import com.demo.entities.Blogs;
import com.demo.entities.Hall;

public interface HallService {

	public List<Hall> findHallsByCinemaId(int cinemaid);

	public boolean save(Hall hall);

	public Hall findHallbyId(int id);

	public Integer saveAndGetId(Hall hall);

	public Map<String, Integer> findRowAndMaxColOfTheRow(Integer hallId);

	public List<HallDTO> findHallDTObyCinemaID(int cinemaid);

	public boolean delete(int id);

}
