package com.demo.entities;
// Generated Mar 8, 2024, 11:16:40 AM by Hibernate Tools 4.3.6.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

@Entity
@Table(name = "shows")
public class Shows implements java.io.Serializable {
	private Integer id;
	private Cinema cinema;
	private Hall hall;
	private Movie movie;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date startTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date endTime;
	@OneToMany(mappedBy = "shows")
	private Set<BookingDetails> bookingDetailses = new HashSet<BookingDetails>(0);

	public Shows() {
	}

	public Shows(Cinema cinema, Movie movie, Date startTime, Date endTime) {
		this.cinema = cinema;
		this.movie = movie;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Shows(Cinema cinema, Movie movie, Date startTime, Date endTime, Set<BookingDetails> bookingDetailses) {
		this.cinema = cinema;
		this.movie = movie;
		this.startTime = startTime;
		this.endTime = endTime;
		this.bookingDetailses = bookingDetailses;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cinema_id", nullable = false)
	public Cinema getCinema() {
		return this.cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hall_id")
	public Hall getHall() {
		return this.hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_id", nullable = false)
	public Movie getMovie() {
		return this.movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time", nullable = false, length = 26)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time", nullable = false, length = 26)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@OneToMany(mappedBy = "shows")
	public Set<BookingDetails> getBookingDetailses() {
		return this.bookingDetailses;
	}

	public void setBookingDetailses(Set<BookingDetails> bookingDetailses) {
		this.bookingDetailses = bookingDetailses;
	}

	@Override
	public String toString() {
		return "Shows [id=" + id + ", cinema=" + cinema.getName() + ", movie=" + movie.getId() + ", startTime="
				+ startTime + ", endTime=" + endTime + "]";
	}

}
