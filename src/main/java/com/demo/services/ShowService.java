package com.demo.services;
import java.util.List;

import org.springframework.lang.Nullable;

import com.demo.dtos.ShowSeatsDTO;
import com.demo.dtos.ShowSeatsOrderingStatus;
import com.demo.entities.Seats;

import com.demo.entities.Shows;

import com.demo.enums.SeatOrderingStatus;


public interface ShowService {
	ShowSeatsOrderingStatus findSeatOrderingStatusOfAShow(Integer showId, @Nullable SeatOrderingStatus seatStatus);
	Double findPrice(Integer showId);

	
	public boolean save(Shows show);
	public List<Shows> FindShowsbyMovieid (int movie_id);
	public Shows findShowsbyId (int id);
	public boolean delete(int id);

	boolean isSeatAnOrderedSeats(List<ShowSeatsDTO> seats, String currentRow, Integer currentNumber);
}
