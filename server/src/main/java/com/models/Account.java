package com.models;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.utils.StringMerger;

@Entity
@Table(name="account")
public class Account {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	
	@Column(length=45)
	@NotNull
    private String password;
	
	@Column(length=20, unique=true)
	@NotNull
    private String phone;
	
	@Column(length=10)
	@NotNull
	private String salt;
	
	public Account() {}
	public Account(String phone, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		this.phone = phone;
		this.salt = StringMerger.getRandomString(8);
		this.password = StringMerger.encodedByMd5(password, this.salt);
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public Integer getId() {
		return id;
	}
	public String getPassword() {
		return password;
	}
	public String getPhone() {
		return phone;
	}
	public String getSalt() {
		return salt;
	}

}
