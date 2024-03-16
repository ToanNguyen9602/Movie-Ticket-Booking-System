package com.demo.services;
import java.util.List;

import com.demo.dtos.ShowSeatsDTO;
import com.demo.dtos.ShowSeatsOrderingStatus;
import com.demo.entities.Seats;
import com.demo.entities.Shows;

public interface ShowService {
	ShowSeatsOrderingStatus findSeatOrderingStatusOfAShow(Integer showId);
	Double findPrice(Integer showId);
	
	public boolean save(Shows show);
	public List<Shows> FindShowsbyMovieid (int movie_id);
	public Shows findShowsbyId (int id);
	public boolean delete(int id);
}
