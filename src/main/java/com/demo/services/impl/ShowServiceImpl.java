package com.demo.services.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.demo.dtos.OrderSeat;
import com.demo.dtos.ShowSeatsDTO;
import com.demo.dtos.ShowSeatsOrderingStatus;
import com.demo.entities.BookingDetails;
import com.demo.entities.Hall;
import com.demo.entities.Seats;
import com.demo.entities.Shows;
import com.demo.enums.SeatOrderingStatus;
import com.demo.helpers.MapUtils;
import com.demo.repositories.BookingRepository;
import com.demo.repositories.HallRepository;
import com.demo.repositories.MovieRepository;
import com.demo.repositories.SeatsRepository;
import com.demo.repositories.ShowsRepository;
import com.demo.services.HallService;
import com.demo.services.ShowService;

@Service
public class ShowServiceImpl implements ShowService {

	private ShowsRepository showsRepository;
	private MovieRepository movieRepository;
	private BookingRepository bookingRepository;
	private HallRepository hallRepository;
	private SeatsRepository seatRepository;
	private HallService hallService;

	@Autowired
	public ShowServiceImpl(ShowsRepository showsRepository, MovieRepository movieRepository,
			BookingRepository bookingRepository, HallRepository hallRepository, SeatsRepository seatsRepository,
			HallService hallService) {
		this.showsRepository = showsRepository;
		this.movieRepository = movieRepository;
		this.bookingRepository = bookingRepository;
		this.hallRepository = hallRepository;
		this.seatRepository = seatsRepository;
		this.hallService = hallService;
	}

	@Override
	public ShowSeatsOrderingStatus findSeatOrderingStatusOfAShow(Integer showId,
			@Nullable SeatOrderingStatus seatStatus) {
		var show = showsRepository.findById(showId).get();
		var bookingDetails = show.getBookingDetailses();
		var hall = show.getHall();

		List<Seats> orderedSeats = bookingDetails.stream().map(BookingDetails::getSeats).toList();
		List<Seats> defaultSeats = getSeats(hall.getId());

		List<ShowSeatsDTO> seats = defaultSeats.stream().map(seat -> {
			var orderedSeat = getSeats(orderedSeats, seat.getRow(), seat.getNumber());
			return mapFromSeat(showId, seat,
					seat.equals(orderedSeat) ? SeatOrderingStatus.ORDERED : SeatOrderingStatus.BLANK);
		}).toList();

		List<ShowSeatsDTO> seats1 = defaultSeats.stream().map(seat -> {
			var orderedSeat = getSeats(orderedSeats, seat.getRow(), seat.getNumber());
			return mapFromSeat(showId, seat,
					seat.equals(orderedSeat) ? SeatOrderingStatus.ORDERED : SeatOrderingStatus.BLANK);
		}).filter(seat -> {
			if (seatStatus == null)
				return true;
			return seat.getStatus().equals(seatStatus);
		}).toList();
		seats1.forEach(System.out::println);

		Map<String, Integer> rowAndMaxNumberOfTheRow = hallService.findRowAndMaxColOfTheRow(hall.getId());

		return new ShowSeatsOrderingStatus(seats1, rowAndMaxNumberOfTheRow,
				MapUtils.getKeyList(rowAndMaxNumberOfTheRow), getListNumberOfLargestRow(rowAndMaxNumberOfTheRow));
	}

	private List<Integer> getListNumberOfLargestRow(Map<String, Integer> rowAndMaxNumberOfTheRow) {
		List<Integer> numbers = MapUtils.getListValue(rowAndMaxNumberOfTheRow, null);
		Integer max = numbers.stream().mapToInt(Integer::intValue).max().getAsInt();
		return IntStream.rangeClosed(1, max).boxed().collect(Collectors.toList());
	}

	private ShowSeatsDTO mapFromSeat(Integer showId, Seats seat, SeatOrderingStatus status) {
		return new ShowSeatsDTO(showId, seat.getId(), seat.getRow(), seat.getNumber(), status);
	}

	private Seats getSeats(List<Seats> seats, String row, Integer number) {
		List<Seats> seatList = seats.stream().filter(s -> s.getRow().equals(row) && s.getNumber() == number).limit(1)
				.toList();
		return seatList.size() > 0 ? seatList.get(0) : null;
	}

	private List<Seats> getSeats(Integer hallId) {
		Seats seat = new Seats();
		Hall hall = new Hall(hallId);
		seat.setHall(hall);
		return seatRepository.findAll(Example.of(seat), Sort.by("row").and(Sort.by("number")));

	}

	@Override
	public Double findPrice(Integer showId) {
		return showsRepository.findById(showId).get().getMovie().getPrice();
	}

	@Override
	public boolean save(Shows show) {
		try {

			showsRepository.save(show);
			return true;
		}

		catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Shows> FindShowsbyMovieid(int movie_id) {
		return showsRepository.ShowsbyMovieid(movie_id);
	}

	@Override
	public Shows findShowsbyId(int id) {
		return showsRepository.findShowsById(id);
	}

	@Override
	public boolean delete(int id) {
		try {
			showsRepository.delete(showsRepository.findShowsById(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean isSeatAnOrderedSeats(List<ShowSeatsDTO> seats, String currentRow, Integer currentNumber) {
		return !(seats.stream()
				.filter(seat -> seat.getRow().equalsIgnoreCase(currentRow) && seat.getNumber() == currentNumber)
				.findAny().isEmpty());
	}

	public List<Shows> SearchShows(int movieId, int CinemaId, Date startdate) {

		return showsRepository.SearchShows(movieId, CinemaId, startdate);
	}

	public List<Shows> SearchShowsNoDate(int movieId, int CinemaId) {
		return showsRepository.SearchShowsNoDate(movieId, CinemaId);
	}

	public Shows FindShowByTimeandHall(int hallid, Date startdate) {
		if (showsRepository.findShowByTimeAndHall(hallid, startdate) != null) {
			return showsRepository.findShowByTimeAndHall(hallid, startdate);
		} else
			return null;
	}

	public List<Shows> findAllShowsByAccountId(int accountId) {
		return showsRepository.findAllShowsByAccountId(accountId);
	}

	@Override
	public List<Seats> mapToSeat(OrderSeat seats) {
		Shows show = showsRepository.findById(seats.getShowId()).get();
		Hall hall = show.getHall();
		List<Seats> defaultSeats = hall.getSeatses();
		List<String> orderSeatIds = Arrays.asList(seats.getBookingSeats().split(";"));
		List<Seats> orderedSeats = orderSeatIds.stream()
			.map(seatId -> {
				String[] rowAndNumber = seatId.split("");
				return findSeatsByRowAndNumber(defaultSeats, rowAndNumber[0], Integer.valueOf(rowAndNumber[1]));
			})
			.toList();
		
		return orderedSeats;
	}
	
	private Seats findSeatsByRowAndNumber(List<Seats> seats, String row, Integer number) {
		return seats.stream()
				.filter(seat -> seat.getRow().equals(row) && seat.getNumber().compareTo(number) == 0)
				.toList()
				.get(0);
	}
	
	public Integer countShowsEnd() {
		return showsRepository.countShowsWithEndTimeBeforeNow();
	}
}

