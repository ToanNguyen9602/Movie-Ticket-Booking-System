package com.demo.services;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.demo.entities.Seats;

public interface SeatsService {

	public boolean save(Seats seats);

	public int countseats(int hallid);

	public List<Seats> findallSeatsbyHallid(int hallid);

	public Integer countUniqueRowsByHallId(int hallId);

	public Integer countUniqueColumnsByHallId(int hallId);
	
	public boolean delete(int id);
	
	public Seats findSeatId(int hallId, String row, int number);


}
