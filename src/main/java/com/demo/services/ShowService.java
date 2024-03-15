package com.demo.services;
import java.util.List;

import com.demo.dtos.ShowSeatsDTO;
import com.demo.dtos.ShowSeatsOrderingStatus;
import com.demo.entities.Seats;

public interface ShowService {
	ShowSeatsOrderingStatus findSeatOrderingStatusOfAShow(Integer showId);
	Double findPrice(Integer showId);
}
