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

import com.utils.Values;

@Embeddable
public class Comment {
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="userId", nullable=false)
	private User author;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;
	
	@Column(length=200)
	@NotNull
    private String content;
	
	@Column
	private Integer likeNum;
	
	@Parent
	private MovieArticle movieArticle;

	public Comment() {}
	
	public Comment(User user, String content) {
		this.likeNum = Values.ZERO;
		this.author = user;
		this.content = content;
		this.dateTime = new Date();
	}

	public User getAuthor() {
		return author;
	}
	public String getContent() {
		return content;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public Integer getLikeNum() {
		return likeNum;
	}
	public MovieArticle getMovieArticle() {
		return movieArticle;
	}
	
	public void setAuthor(User author) {
		this.author = author;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public void setLikeNum(Integer likeNum) {
		this.likeNum = likeNum;
	}
	public void setMovieArticle(MovieArticle movieArticle) {
		this.movieArticle = movieArticle;
	}
	
}
