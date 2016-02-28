package com.showflix.app.service;

import com.showflix.app.dao.entity.ShowRating;
import com.showflix.app.service.exceptions.ServiceException;

public interface IRatingService {
	
	public void addRating(ShowRating rating) throws ServiceException;
	
	public double getAvgRatingForShow(String imdbId) throws ServiceException;
	
	
}
