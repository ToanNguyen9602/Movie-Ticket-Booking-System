package com.demo.dtos;

import java.util.LinkedList;
import java.util.LinkedList;

public class BookingDTO {
	public LinkedList<FoodBookingDTO> foods;
    public LinkedList<String> seatIds;
    public Integer showId;
    public Double showPrice;
    
    public BookingDTO() {
    	
    }
    
	public BookingDTO(LinkedList<FoodBookingDTO> foods, LinkedList<String> seatIds, Integer showId, Double showPrice) {
		
		this.foods = foods;
		this.seatIds = seatIds;
		this.showId = showId;
		this.showPrice = showPrice;
	}

	public LinkedList<FoodBookingDTO> getFoods() {
		return foods;
	}

	public void setFoods(LinkedList<FoodBookingDTO> foods) {
		this.foods = foods;
	}

	public LinkedList<String> getSeatIds() {
		return seatIds;
	}

	public void setSeatIds(LinkedList<String> seatIds) {
		this.seatIds = seatIds;
	}

	public Integer getShowId() {
		return showId;
	}

	public void setShowId(Integer showId) {
		this.showId = showId;
	}

	public Double getShowPrice() {
		return showPrice;
	}

	public void setShowPrice(Double showPrice) {
		this.showPrice = showPrice;
	}
	
}
