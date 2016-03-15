package com.showflix.app.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity 
public class ShowRating {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RatingId")
	private Integer id;
	@Column(name="Rating")
	private double rating;
	@Column(name="UserName")
	private String userName;
	@Column(name="ImdbID")
	private String imdbId;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

}
