package com.demo.dtos;

import java.util.List;

public class FoodBookingDTO {
	public String id;
    public Integer bookingId;
    public String price;
    public String name;
    public String quantity;
    
    public FoodBookingDTO () {
    	
    }
	public FoodBookingDTO(String id, String price, String name, String quantity) {
		super();
		this.id = id;
		this.price = price;
		this.name = name;
		this.quantity = quantity;
	}
	public FoodBookingDTO(Integer bookingId, String price, String name, String quantity) {
		super();
		this.bookingId = bookingId;
		this.price = price;
		this.name = name;
		this.quantity = quantity;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "FoodBookingDTO [id=" + id + ", price=" + price + ", name=" + name + ", quantity=" + quantity + "]";
	}
    
	
	
    
}
