package com.modelboosters;

public class Grade {
	private Integer userId;
	private Integer value;
	private String content;
	private String dataTime;
	
	public Grade(Integer userId, Integer value, String content, String dateTime) {
		this.userId = userId;
		this.value = value;
		this.content = content;
		this.dataTime = dateTime;
	}
	public String getContent() {
		return content;
	}
	public String getDataTime() {
		return dataTime;
	}
	public Integer getUserId() {
		return userId;
	}
	public Integer getValue() {
		return value;
	}
}
