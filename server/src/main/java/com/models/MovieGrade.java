package com.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Parent;

@Embeddable
public class MovieGrade {
	
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="userId", nullable=false)
	private User user;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;
	
	@Column(columnDefinition="TINYINT(2) NOT NULL")
	private Integer value;
	
	@NotNull
	@Column(length=300)
	private String content;
	
	@Parent
	private MovieDescription movieDescription;
	
	public MovieGrade() {}
	
	public MovieGrade(User user, Integer value, String content) {
		this.user = user;
		this.value = value;
		this.content = content;
		this.dateTime = new Date();
	}
	
	public String getContent() {
		return content;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public MovieDescription getMovieDescription() {
		return movieDescription;
	}
	public User getUserId() {
		return user;
	}
	public User getUser() {
		return user;
	}
	public Integer getValue() {
		return value;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public void setMovieDescription(MovieDescription movieDescription) {
		this.movieDescription = movieDescription;
	}
	public void setUserId(User userId) {
		this.user = userId;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	
}
