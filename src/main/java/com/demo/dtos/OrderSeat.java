package com.demo.dtos;


public class OrderSeat {
	private Integer showId;
	private String bookingSeats;
	
	public OrderSeat() {
		
	}
	
	public OrderSeat(Integer showId, String bookingSeats) {
		super();
		this.showId = showId;
		this.bookingSeats = bookingSeats;
	}

	public Integer getShowId() {
		return showId;
	}

	public void setShowId(Integer showId) {
		this.showId = showId;
	}

	public String getBookingSeats() {
		return bookingSeats;
	}

	public void setBookingSeats(String bookingSeats) {
		this.bookingSeats = bookingSeats;
	}
	
}
