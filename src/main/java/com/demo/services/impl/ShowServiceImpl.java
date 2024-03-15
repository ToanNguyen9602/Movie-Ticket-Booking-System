package com.demo.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.demo.dtos.ShowSeatsDTO;
import com.demo.entities.BookingDetails;
import com.demo.entities.Hall;
import com.demo.entities.Seats;
import com.demo.enums.SeatOrderingStatus;
import com.demo.repositories.BookingRepository;
import com.demo.repositories.HallRepository;
import com.demo.repositories.MovieRepository;
import com.demo.repositories.SeatsRepository;
import com.demo.repositories.ShowsRepository;
import com.demo.services.ShowService;


@Service
public class ShowServiceImpl implements ShowService {

	private ShowsRepository showsRepository;
	private MovieRepository movieRepository;
	private BookingRepository bookingRepository;
	private HallRepository hallRepository;
	private SeatsRepository seatRepository;
	@Autowired
	public ShowServiceImpl(ShowsRepository showsRepository, MovieRepository movieRepository, BookingRepository bookingRepository, HallRepository hallRepository, 
			SeatsRepository seatsRepository){
		this.showsRepository = showsRepository;
		this.movieRepository = movieRepository;
		this.bookingRepository = bookingRepository;
		this.hallRepository = hallRepository;
		this.seatRepository = seatsRepository;
	}

	@Override
	public List<ShowSeatsDTO> findAllSeats(Integer showId) {
		var show = showsRepository.findById(showId).get();
		var bookingDetails = show.getBookingDetailses();
		List<Seats> orderedSeats = bookingDetails.stream().map(BookingDetails::getSeats).toList();
		List<Seats> defaultSeats = getSeats(show.getHall().getId());
		List<ShowSeatsDTO> seats = defaultSeats.stream()
				.map(seat -> {
					var orderedSeat = getSeats(orderedSeats, seat.getRow(), seat.getNumber());
					return mapFromSeat(showId, seat, seat.equals(orderedSeat) ? SeatOrderingStatus.ORDERED : SeatOrderingStatus.BLANK);
				})
				.toList();
		
		return seats;
	}
	
	private ShowSeatsDTO mapFromSeat(Integer showId, Seats seat, SeatOrderingStatus status) {
		return new ShowSeatsDTO(showId, seat.getId(), seat.getRow(), seat.getNumber(), status);
	}
	
	private Seats getSeats(List<Seats> seats, String row, Integer number) {
		List<Seats> seatList = seats.stream().filter(s -> s.getRow().equals(row) && s.getNumber() == number).limit(1).toList();
		return seatList.size() > 0 ? seatList.get(0) : null;
	}
	
	private List<Seats> getSeats(Integer hallId) {
		Seats seat = new Seats();
		Hall hall = new Hall(hallId);
		seat.setHall(hall);
		return seatRepository.findAll(Example.of(seat), Sort.by("row").and(Sort.by("number")));
		
	}

}
