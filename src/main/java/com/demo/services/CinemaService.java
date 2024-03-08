package com.demo.services;




import com.demo.entities.Cinema;


public interface CinemaService {
<<<<<<< HEAD
	public boolean save(Cinema cinema);
	public Iterable<Cinema> findAll();
	public Cinema findCinemasById(int id);
=======
	boolean save(Cinema cinema);
	
	List<Movie> findAllMovies(Integer cinemaId);
	
>>>>>>> 045493b2e2f6feb7b60944e39cbc9d528d7029e5
}
