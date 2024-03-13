package com.demo.services;
import com.demo.entities.Seats;



public interface SeatsService {


	public boolean save(Seats seats);
	public int countseats(int hallid);
	
	
	

}
