package com.demo.dtos;

import java.util.Date;

import com.demo.enums.SeatOrderingStatus;

public class ShowSeatsDTO {
	private Integer showId;
	private Integer seatId;
	private String row;
	private Integer number;
	private SeatOrderingStatus status;
	
	public ShowSeatsDTO() {
		
	}
	
	public ShowSeatsDTO(Integer showId, Integer seatId, String row, Integer number, SeatOrderingStatus status) {
		super();
		this.showId = showId;
		this.seatId = seatId;
		this.row = row;
		this.number = number;
		this.status = status;
	}

	public Integer getShowId() {
		return showId;
	}

	public void setShowId(Integer showId) {
		this.showId = showId;
	}

	public Integer getSeatId() {
		return seatId;
	}

	public void setSeatId(Integer seatId) {
		this.seatId = seatId;
	}

	public String getRow() {
		return row;
	}
	public void setRow(String row) {
		this.row = row;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public SeatOrderingStatus getStatus() {
		return status;
	}

	public void setStatus(SeatOrderingStatus status) {
		this.status = status;
	}
 
	@Override
	public String toString() {
		return "ShowSeatsDTO [showId=" + showId + ", seatId=" + seatId + ", row=" + row + ", number=" + number
				+ ", status=" + status + "]";
	}
	
}
