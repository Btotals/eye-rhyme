package com.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="movieProduct")
public class MovieProduct {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	
	@ManyToOne(targetEntity=MovieDescription.class)
	@JoinColumn(name="movieDescription", nullable=false)
	private MovieDescription movieDescription;
	
	@ManyToOne(targetEntity=Theater.class)
	@JoinColumn(name="theater", nullable=false)
	private Theater theater;
	
	@Column(columnDefinition="TINYINT(2) NOT NULL")
	private Integer type;
	
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date date;
	
	@Column(columnDefinition="TINYINT(2) NOT NULL")
	private Integer round;
	
	@Column(columnDefinition="TINYINT(2) NOT NULL")
	private Integer hall;
	
	@Column(columnDefinition="SMALLINT(3) NOT NULL")
    private Integer price;
	
	@Column(columnDefinition="SMALLINT(3) default 0")
    private Integer discount;
	
	@ElementCollection(targetClass=Integer.class, fetch=FetchType.EAGER)
	@CollectionTable(name="seat", joinColumns=@JoinColumn(name="movieProductId", nullable=false))
	@Column(name="positionNum")
	private Set<Integer> seats = new HashSet<Integer>();
	
	public MovieProduct() {}
	
	public MovieProduct(MovieDescription movieDescription, Theater theater, Integer type, Date date,
			Integer round, Integer hall, Integer price, Integer discount) {
		this.movieDescription = movieDescription;
		this.theater = theater;
		this.type = type;
		this.date = date;
		this.round = round;
		this.hall = hall;
		this.price = price;
		this.discount = discount;
	}
	
	public Set<Integer> getSeats() {
		return seats;
	}
	
	public Date getDate() {
		return date;
	}
	public Integer getDiscount() {
		return discount;
	}
	public Integer getHall() {
		return hall;
	}
	public Integer getPrice() {
		return price;
	}
	public Integer getRound() {
		return round;
	}
	public Integer getType() {
		return type;
	}
	public Integer getId() {
		return id;
	}
	public MovieDescription getMovieDescription() {
		return movieDescription;
	}
	public Theater getTheater() {
		return theater;
	}
	
	
	public void setDate(Date date) {
		this.date = date;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setMovieDescription(MovieDescription movieDescription) {
		this.movieDescription = movieDescription;
	}
	public void setTheater(Theater theater) {
		this.theater = theater;
	}
	public void setSeats(Set<Integer> seats) {
		this.seats = seats;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public void setHall(Integer hall) {
		this.hall = hall;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public void setRound(Integer round) {
		this.round = round;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}
