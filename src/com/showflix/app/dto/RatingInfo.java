package com.showflix.app.dto;

public class RatingInfo {

	private Double rating;
	
	public RatingInfo(double rating){
		this.setRating(rating);
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}
	
}
