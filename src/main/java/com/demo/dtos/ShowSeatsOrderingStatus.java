package com.demo.dtos;

import java.util.List;
import java.util.Map;

public class ShowSeatsOrderingStatus {
	private List<ShowSeatsDTO> seats;
	private Map<String, Integer> rowAndMaxRowNumberMap; // a map created for storing the Row name, and max Number of that row
	private List<String> rowNames; // a list for storing from start row, to end row
	private List<Integer> maxNumbers; // a list for storing from start number to largest number of the row (has largest number)
	public ShowSeatsOrderingStatus() {
		
	}
	
	public ShowSeatsOrderingStatus(List<ShowSeatsDTO> seats, Map<String, Integer> rowAndMaxRowNumberMap) {
		super();
		this.seats = seats;
		this.rowAndMaxRowNumberMap = rowAndMaxRowNumberMap;
	}
	
	
	

	public ShowSeatsOrderingStatus(List<ShowSeatsDTO> seats, Map<String, Integer> rowAndMaxRowNumberMap,
			List<String> rowNames, List<Integer> maxNumbers) {
		super();
		this.seats = seats;
		this.rowAndMaxRowNumberMap = rowAndMaxRowNumberMap;
		this.rowNames = rowNames;
		this.maxNumbers = maxNumbers;
	}

	public List<ShowSeatsDTO> getSeats() {
		return seats;
	}

	public void setSeats(List<ShowSeatsDTO> seats) {
		this.seats = seats;
	}

	public Map<String, Integer> getRowAndMaxRowNumberMap() {
		return rowAndMaxRowNumberMap;
	}

	public void setRowAndMaxRowNumberMap(Map<String, Integer> rowAndMaxRowNumberMap) {
		this.rowAndMaxRowNumberMap = rowAndMaxRowNumberMap;
	}

	public List<String> getRowNames() {
		return rowNames;
	}

	public void setRowNames(List<String> rowNames) {
		this.rowNames = rowNames;
	}

	public List<Integer> getMaxNumbers() {
		return maxNumbers;
	}

	public void setMaxNumbers(List<Integer> maxNumbers) {
		this.maxNumbers = maxNumbers;
	}

	
	
}
