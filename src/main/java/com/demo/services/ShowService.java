package com.demo.services;
import java.util.List;

import com.demo.dtos.ShowSeatsDTO;
import com.demo.entities.Seats;

public interface ShowService {
	List<ShowSeatsDTO> findAllSeats(Integer showId);
}
