package com.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.utils.Values;

@Entity
@Table(name="MovieActivity")
public class MovieActivity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="launcherId", nullable=false)
	private User launcher;
	
	@ManyToOne(targetEntity=MovieDescription.class)
	@JoinColumn(name="movieDescriptionId", nullable=false)
	private MovieDescription movieDescription;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;
	
	@Column(columnDefinition="TINYINT(1) DEFAULT 0")
	private Integer state;
	
	@Column(length=45)
	@NotNull
    private String place;
	
	@Column(columnDefinition="DECIMAL(10, 7) default 999.9999999")
	private BigDecimal longitude;
	
	@Column(columnDefinition="DECIMAL(10, 7) default 999.9999999")
	private BigDecimal latitude;
	
	@Column(length=45)
	@NotNull
    private String contact;
	
	@Column
    private Integer joinBound;
	
	@Column(length=300)
	@NotNull
    private String content;
	
	/*@ElementCollection(targetClass=JoinActivity.class, fetch=FetchType.EAGER)
	@CollectionTable(name="JoinActivity", joinColumns=@JoinColumn(name="MovieActivityId", nullable=false))
	@Column(name="MovieActivityId")
	private Set<JoinActivity> joins = new HashSet<JoinActivity>();*/
	
	@ManyToMany(targetEntity=User.class, fetch=FetchType.EAGER)
	@JoinTable(name="join_activity", joinColumns=@JoinColumn(name="activity_id", referencedColumnName="id"),
	inverseJoinColumns=@JoinColumn(name="user_id", referencedColumnName="id"))
	private Set<User> participants = new HashSet<User>();
	
	public MovieActivity() {}
	
	public MovieActivity(Date dateTime, String place, String content, BigDecimal longitude, BigDecimal latitude, String contact, Integer bound) {
		this.dateTime = dateTime;
		this.place = place;
		this.longitude = longitude;
		this.latitude = latitude;
		this.contact = contact;
		this.content = content;
		this.state = Values.ZERO;
		this.joinBound = bound;
	}
	
	public String getContact() {
		return contact;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public Integer getId() {
		return id;
	}
	
	public Set<User> getParticipants() {
		return participants;
	}
	public Integer getState() {
		return state;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public MovieDescription getMovieDescription() {
		return movieDescription;
	}
	public String getPlace() {
		return place;
	}
	public User getLauncher() {
		return launcher;
	}
	public String getContent() {
		return content;
	}
    public Integer getJoinBound() {
		return joinBound;
	}
	
	public void setContact(String contact) {
		this.contact = contact;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setParticipants(Set<User> participants) {
		this.participants = participants;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public void setMovieDescription(MovieDescription movieDescription) {
		this.movieDescription = movieDescription;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public void setLauncher(User launcher) {
		this.launcher = launcher;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setJoinBound(Integer joinBound) {
		this.joinBound = joinBound;
	}
	
}
