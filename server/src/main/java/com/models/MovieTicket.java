package com.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import com.utils.Values;

@Entity
@Table(name="MovieTicket")
public class MovieTicket {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	
	@ManyToOne(targetEntity=MovieProduct.class)
	@JoinColumn(name="movieProductId", nullable=false)
	private MovieProduct movieProduct;
	
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="userId", nullable=false)
	private User user;
	
	@Column(columnDefinition="SMALLINT(6) NOT NULL")
	private Integer positionNum;
	
	@Column(columnDefinition="TINYINT(3) DEFAULT 0")
	private Integer type;
	
	@Column(columnDefinition="TINYINT(3) DEFAULT 0")
	private Integer status;
	
	@NotNull
	@Column(length=10)
	private String receiptNum;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date expireTime;
	
	public MovieTicket() {
		this.expireTime = new Date(new Date().getTime() + Values.ExpireTime);
	}
	
	public MovieTicket(User user, MovieProduct movieProduct, Integer positionNum, Integer type, Integer status, String receiptNum) {
		this.positionNum = positionNum;
		this.type = type;
		this.status = status;
		this.user = user;
		this.movieProduct = movieProduct;
		this.receiptNum = receiptNum;
		this.expireTime = new Date(new Date().getTime() + Values.ExpireTime);
	}
	
	public Date getExpireTime() {
		return expireTime;
	}
	public Integer getId() {
		return id;
	}
	public MovieProduct getMovieProduct() {
		return movieProduct;
	}
	public User getUser() {
		return user;
	}
	
	public Integer getPositionNum() {
		return positionNum;
	}
	public Integer getStatus() {
		return status;
	}
	public Integer getType() {
		return type;
	}
	public String getReceiptNum() {
		return receiptNum;
	}
	
	
	public void setId(Integer id) {
		this.id = id;
	}
	public void setMovieProduct(MovieProduct movieProduct) {
		this.movieProduct = movieProduct;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}
	public void setPositionNum(Integer positionNum) {
		this.positionNum = positionNum;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

}
