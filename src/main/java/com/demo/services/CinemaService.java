package com.demo.services;




import com.demo.entities.Cinema;





public interface CinemaService {
	public boolean save(Cinema cinema);
	public Iterable<Cinema> findAll();
	public Cinema findCinemasById(int id);
}
