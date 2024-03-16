package com.demo.entities;
// Generated Mar 8, 2024, 12:54:43 PM by Hibernate Tools 4.3.6.Final

import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

/**
 * Account generated by hbm2java
 */
@Entity
@Table(name = "account")
public class Account implements java.io.Serializable {

	private Integer id;
	@NotEmpty
	@Length(min = 7, max = 20)
	private String username;
	@NotEmpty
	@Length(min = 7, max = 60)
	private String password;
	@Email
	@NotEmpty
	private String email;
	@NotEmpty
	private String phone;
	@NotEmpty
	private String fullname;
	@NotEmpty
	private String address;
	private Boolean status;
	private Set<Booking> bookings = new HashSet<Booking>(0);
	private Set<Role> roles = new HashSet<Role>(0);
	private Set<Blogs> blogses = new HashSet<Blogs>(0);

	public Account() {
	}

	public Account(String username, String password, String email, String phone, String fullname, String address,
			boolean status) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.fullname = fullname;
		this.address = address;
		this.status = status;
	}

	public Account(String username, String password, String email, String phone, String fullname, String address,
			boolean status, Set<Booking> bookings, Set<Role> roles, Set<Blogs> blogses) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.fullname = fullname;
		this.address = address;
		this.status = status;
		this.bookings = bookings;
		this.roles = roles;
		this.blogses = blogses;
	}

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "username", nullable = false)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", nullable = false)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "email", nullable = false)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "phone", nullable = false)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "fullname", nullable = false)
	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	@Column(name = "address", nullable = false, length = 65535)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "status", nullable = false)
	public boolean isStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
	public Set<Booking> getBookings() {
		return this.bookings;
	}

	public void setBookings(Set<Booking> bookings) {
		this.bookings = bookings;
	}

	 @ManyToMany(fetch = FetchType.LAZY)
	    @JoinTable(name = "account_role",
	        joinColumns = @JoinColumn(name = "account_id", nullable = false, updatable = false, insertable = false),
	        inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false, updatable = false, insertable = false))
	    public Set<Role> getRoles() {
	        return this.roles;
	    }

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
	public Set<Blogs> getBlogses() {
		return this.blogses;
	}

	public void setBlogses(Set<Blogs> blogses) {
		this.blogses = blogses;
	}

}
