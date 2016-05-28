package com.models;

import java.math.BigDecimal;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="theater")
public class Theater {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	
	@Column(length=50, unique=true)
	@NotNull
    private String name;
	
	@Column(length=50)
	@NotNull
    private String location;
	
	@Column(length=20)
	@NotNull
	private String contact;
	
	@Column(length=300)
	@NotNull
	private String description;
	
	@Column(columnDefinition="DECIMAL(10, 7) default 999.9999999")
	private BigDecimal longitude;
	
	@Column(columnDefinition="DECIMAL(10, 7) default 999.9999999")
	private BigDecimal latitude;
	
	@ElementCollection(targetClass=TheaterGrade.class, fetch=FetchType.EAGER)
	@CollectionTable(name="theater_grade", joinColumns=@JoinColumn(name="theaterId", nullable=false))
	@Column(name="theaterId")
	private Set<TheaterGrade> grades = new HashSet<TheaterGrade>();
	
	public Theater() {}
	
	public Theater(String name, String location, String contact, String description,
			BigDecimal longitude, BigDecimal latitude) {
		this.name = name;
		this.location = location;
		this.contact = contact;
		this.description = description;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public String getContact() {
		return contact;
	}
	public String getDescription() {
		return description;
	}
	public Integer getId() {
		return id;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public String getLocation() {
		return location;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public String getName() {
		return name;
	}
	public Set<TheaterGrade> getGrades() {
		return grades;
	}
	
	public void setContact(String contact) {
		this.contact = contact;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setGrades(Set<TheaterGrade> grades) {
		this.grades = grades;
	}
}
