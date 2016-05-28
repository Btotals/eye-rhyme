package com.modelboosters;

import java.util.HashMap;
import java.util.Map;

import com.utils.Keyword;

import net.sf.json.JSONObject;

public class Product {
	private Integer id;
	private Integer price;
	private Integer round;
	private Integer hall;
	private Integer type;
	private Integer discount;
	
	public Product(Integer id, Integer price, Integer round, Integer hall, Integer type,
			Integer discount) {
		super();
		this.id = id;
		this.price = price;
		this.round = round;
		this.hall = hall;
		this.type = type;
		this.discount = discount;
	}

	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}	
	
	public Integer getHall() {
		return hall;
	}
	public Integer getId() {
		return id;
	}
	public Integer getPrice() {
		return price;
	}
	public Integer getRound() {
		return round;
	}
	public Integer getType() {
		return type;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public void setRound(Integer round) {
		this.round = round;
	}

	public void setHall(Integer hall) {
		this.hall = hall;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public String toJsonString() {
		Map<String, String> tmap = new HashMap<String, String>();
		tmap.put(Keyword.PRODUCT_ID, id.toString());
        tmap.put(Keyword.PRICE, price.toString());
        tmap.put(Keyword.ROUND, round.toString());
        tmap.put(Keyword.HALL, hall.toString());
        tmap.put(Keyword.TYPE, type.toString());
        tmap.put(Keyword.DISCOUNT, discount.toString());
        return JSONObject.fromObject(tmap).toString();
	}
}
