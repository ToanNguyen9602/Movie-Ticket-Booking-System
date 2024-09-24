package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.demo.entities.Hall;
import com.demo.entities.Movie;
import com.demo.entities.Seats;

public interface SeatsRepository extends JpaRepository<Seats, Integer> {

	@Query("Select count(*) from Seats where hall.id=:hallid")
	public Integer countseatsByHallid(@Param("hallid") int hallid);

	@Query("from Seats where hall.id=:hallid")
	public List<Seats> findallSeatsbyHallid(@Param("hallid") int hallid);

	@Query("SELECT COUNT(DISTINCT s.row) FROM Seats s WHERE s.hall.id = :hallId")
	public Integer countUniqueRowsByHallId(@Param("hallId") int hallId);

	@Query("SELECT COUNT(DISTINCT s.number) FROM Seats s WHERE s.hall.id = :hallId")
	public Integer countUniqueColumnsByHallId(@Param("hallId") int hallId);

	@Query("from Seats where hall.id=:hallId and row=:row and number=:number")
	public Seats findSeatId(@Param("hallId") int hallId, @Param("row") String row, @Param("number") int number);
}
