package com.demo.entities;
// Generated Mar 4, 2024, 1:08:42 PM by Hibernate Tools 4.3.6.Final

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

/**
 * MovieShow generated by hbm2java
 */
@Entity
@Table(name = "movie_show")
public class MovieShow implements java.io.Serializable {

	private Integer id;
	
	private BookingDetails bookingDetails;
	
	private Shows shows;
	
	@DateTimeFormat(pattern = "HH:mm:ss")
	private Date startTime;
	
	@DateTimeFormat(pattern = "HH:mm:ss")
	private Date endTime;

	public MovieShow() {
	}

	public MovieShow(BookingDetails bookingDetails, Shows shows, Date startTime, Date endTime) {
		this.bookingDetails = bookingDetails;
		this.shows = shows;
		this.startTime = startTime;
		this.endTime = endTime;
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
	@JoinColumns({
        @JoinColumn(name = "booking_id", referencedColumnName = "booking_id"),
        @JoinColumn(name = "movie_show_id", referencedColumnName = "movie_show_id"),
        @JoinColumn(name = "seats_id", referencedColumnName = "seats_id")
    })
	public BookingDetails getBookingDetails() {
		return this.bookingDetails;
	}

	public void setBookingDetails(BookingDetails bookingDetails) {
		this.bookingDetails = bookingDetails;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shows_id", nullable = false)
	public Shows getShows() {
		return this.shows;
	}
	

	public void setShows(Shows shows) {
		this.shows = shows;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "start_time", nullable = false, length = 16)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "end_time", nullable = false, length = 16)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
