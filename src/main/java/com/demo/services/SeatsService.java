package com.demo.services;
import java.util.List;

import com.demo.entities.Seats;



public interface SeatsService {


	public boolean save(Seats seats);
	public int countseats(int hallid);
	public List<Seats> findallSeatsbyHallid(int hallid);
	
	
	
	

}
