package com.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.utils.Values;


@Entity
@Table(name="user")
public class User {
	@Id
	private Integer id;
	
	@NotNull
	@Column(length=45)
	private String name;
	
	@Column(columnDefinition="TINYINT(1) default 0")
	private Integer gender;
	
	@NotNull
	@Column(length=20)
	private String phone;
	
	@Column(length=45, nullable=true)
	private String email;
	
	@Temporal(TemporalType.DATE)
	private Date birthday;
	
	@Column(length=60, nullable=true)
	private String signature;
	
	@Column(length=60, nullable=true)
	private String hobby;
	
	@Column(columnDefinition="DECIMAL(3, 2) default 0.0")
	private BigDecimal rank;
	
	@ManyToMany(targetEntity=MovieActivity.class, fetch=FetchType.EAGER)
	@JoinTable(name="join_activity", joinColumns=@JoinColumn(name="user_id", referencedColumnName="id"),
	inverseJoinColumns=@JoinColumn(name="activity_id", referencedColumnName="id"))
	private Set<MovieActivity> joins = new HashSet<MovieActivity>();
	
	public User() {}
	
	public User(Integer id, String name, String phone) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.rank = new BigDecimal("0.00");
		this.gender = Values.ZERO;
	}

	public String getHobby() {
		return hobby;
	}
	public String getPhone() {
		return phone;
	}
	public String getSignature() {
		return signature;
	}
	public Date getBirthday() {
		return birthday;
	}
	public String getEmail() {
		return email;
	}
	public Integer getGender() {
		return gender;
	}
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public BigDecimal getRank() {
		return rank;
	}
	public Set<MovieActivity> getJoins() {
		return joins;
	}
	
	public void setJoins(Set<MovieActivity> joins) {
		this.joins = joins;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setRank(BigDecimal rank) {
		this.rank = rank;
	}
	
}
