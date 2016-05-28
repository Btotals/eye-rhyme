package com.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;


import com.utils.Values;

@Entity
@Table(name="MovieArticle")
public class MovieArticle {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="authorId", nullable=false)
	private User author;
	
	@ManyToOne(targetEntity=MovieDescription.class)
	@JoinColumn(name="movieDescriptionId", nullable=false)
	private MovieDescription movieDescription;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;
	
	@Column(length=45)
	@NotNull
    private String title;
	
	@Column(length=1000)
	@NotNull
    private String content;
	
	@Column(length=200)
    private String contentPic;
	
	@Column(nullable=false)
	private Integer readNum;
	
	@Column(nullable=false)
	private Integer likeNum;
	
	@ElementCollection(targetClass=Comment.class, fetch=FetchType.EAGER)
	@CollectionTable(name="Comment", joinColumns=@JoinColumn(name="MovieArticleId", nullable=false))
	@Column(name="MovieArticleId")
	@OrderColumn(name="comment_id")
	private List<Comment> comments = new ArrayList<Comment>();
	
	public MovieArticle() {}
	
	public MovieArticle(User author, MovieDescription movieDescription, String title, String content, String contentPic) {
		this.dateTime = new Date();
		this.author = author;
		this.movieDescription = movieDescription;
		this.title = title;
		this.content = content;
		this.contentPic = contentPic;
		this.readNum = Values.ZERO;
		this.likeNum = Values.ZERO;
	}
	
	public User getAuthor() {
		return author;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public String getContent() {
		return content;
	}
	public String getContentPic() {
		return contentPic;
	}
	public Date getDate() {
		return dateTime;
	}
	public Integer getId() {
		return id;
	}
	public Integer getLikeNum() {
		return likeNum;
	}
	public MovieDescription getMovieDescription() {
		return movieDescription;
	}
	public Integer getReadNum() {
		return readNum;
	}
	public String getTitle() {
		return title;
	}
	
	public void setAuthor(User author) {
		this.author = author;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setContentPic(String contentPic) {
		this.contentPic = contentPic;
	}
	public void setDate(Date dateTime) {
		this.dateTime = dateTime;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setLikeNum(Integer likeNum) {
		this.likeNum = likeNum;
	}
	public void setMovieDescription(MovieDescription movieDescription) {
		this.movieDescription = movieDescription;
	}
	public void setReadNum(Integer readNum) {
		this.readNum = readNum;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
