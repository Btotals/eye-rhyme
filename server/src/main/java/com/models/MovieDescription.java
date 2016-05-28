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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="movie_description")
public class MovieDescription {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	
	@Column(length=45)
	@NotNull
    private String name;
	
	@Column(columnDefinition="TINYINT(2) default 0")
	private Integer type;
	
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date releaseDate;
	
	@Column(columnDefinition="SMALLINT(3) default 90")
    private Integer duration;
	
	@Column(length=50)
	@NotNull
    private String directors;
	
	@Column(length=50)
	@NotNull
	private String actors;
	
	@Column(length=300)
	@NotNull
	private String description;
	
	/*@ElementCollection(targetClass=MovieGrade.class, fetch=FetchType.EAGER)
	@CollectionTable(name="movie_grade", joinColumns=@JoinColumn(name="movieDescriptionId", nullable=false))
	@Column(name="movieDescriptionId")
	@OrderColumn(name="grade_id")
	private List<MovieGrade> grades = new ArrayList<MovieGrade>();*/
	
	@ElementCollection(targetClass=MovieGrade.class, fetch=FetchType.EAGER)
	@CollectionTable(name="movie_grade", joinColumns=@JoinColumn(name="movieDescriptionId", nullable=false))
	@Column(name="movieDescriptionId")
	private Set<MovieGrade> grades = new HashSet<MovieGrade>();

	public MovieDescription() {}
	
	public MovieDescription(String name, Integer type, Date releaseDate, Integer duration, 
			String directors, String actors, String description) {
		this.name = name;
		this.type = type;
		this.releaseDate = releaseDate;
		this.duration = duration;
		this.directors = directors;
		this.actors = actors;
		this.description = description;
	}
	public Set<MovieGrade> getGrades() {
		return grades;
	}
	public String getActors() {
		return actors;
	}
	public String getDescription() {
		return description;
	}
	public String getDirectors() {
		return directors;
	}
	public Integer getDuration() {
		return duration;
	}
	public Integer getType() {
		return type;
	}
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	
	
	public void setActors(String actors) {
		this.actors = actors;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setDirectors(String directors) {
		this.directors = directors;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setGrades(Set<MovieGrade> grades) {
		this.grades = grades;
	}
}
