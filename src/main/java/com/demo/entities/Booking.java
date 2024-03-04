package com.demo.entities;
// Generated Mar 4, 2024, 1:08:42 PM by Hibernate Tools 4.3.6.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;

/**
 * Booking generated by hbm2java
 */
@Entity
@Table(name = "booking")
public class Booking implements java.io.Serializable {

	private Integer id;
	private Account account;
	private Date created;
	private boolean status;
	private Set<FoodBookingDetails> foodBookingDetailses = new HashSet<FoodBookingDetails>(0);
	private Set<Voucher> vouchers = new HashSet<Voucher>(0);
	private Set<BookingDetails> bookingDetailses = new HashSet<BookingDetails>(0);

	public Booking() {
	}

	public Booking(Account account, Date created, boolean status) {
		this.account = account;
		this.created = created;
		this.status = status;
	}

	public Booking(Account account, Date created, boolean status, Set<FoodBookingDetails> foodBookingDetailses,
			Set<Voucher> vouchers, Set<BookingDetails> bookingDetailses) {
		this.account = account;
		this.created = created;
		this.status = status;
		this.foodBookingDetailses = foodBookingDetailses;
		this.vouchers = vouchers;
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
	@JoinColumn(name = "account_id", nullable = false)
	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "created", nullable = false, length = 10)
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Column(name = "status", nullable = false)
	public boolean isStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "booking")
	public Set<FoodBookingDetails> getFoodBookingDetailses() {
		return this.foodBookingDetailses;
	}

	public void setFoodBookingDetailses(Set<FoodBookingDetails> foodBookingDetailses) {
		this.foodBookingDetailses = foodBookingDetailses;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "voucher_booking", joinColumns = @JoinColumn(name = "booking_id", insertable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "voucher_id", insertable = false, updatable = false))
	public Set<Voucher> getVouchers() {
		return this.vouchers;
	}

	public void setVouchers(Set<Voucher> vouchers) {
		this.vouchers = vouchers;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "booking")
	public Set<BookingDetails> getBookingDetailses() {
		return this.bookingDetailses;
	}

	public void setBookingDetailses(Set<BookingDetails> bookingDetailses) {
		this.bookingDetailses = bookingDetailses;
	}

}
