package com.demo.services;
import java.util.List;

import org.springframework.lang.Nullable;

import com.demo.dtos.ShowSeatsDTO;
import com.demo.dtos.ShowSeatsOrderingStatus;
import com.demo.entities.Seats;
import com.demo.enums.SeatOrderingStatus;

public interface ShowService {
	ShowSeatsOrderingStatus findSeatOrderingStatusOfAShow(Integer showId, @Nullable SeatOrderingStatus seatStatus);
	Double findPrice(Integer showId);
	boolean isSeatAnOrderedSeats(List<ShowSeatsDTO> seats, String currentRow, Integer currentNumber);
}
