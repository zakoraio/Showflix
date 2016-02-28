package com.showflix.app.dao;

import java.util.List;

import com.showflix.app.dao.entity.ShowRating;
import com.showflix.app.dao.exceptions.DAOException;

public interface IRatingDao {
	
	public void createRating(ShowRating rating) throws DAOException;
	
	public List<ShowRating> getAllRatingForShow(String imdbID) throws DAOException;
	
	public void updateRating(ShowRating rating) throws DAOException;

	public ShowRating getRatingForShowByUser(String imdbId , String userName) throws DAOException;
	
	public List<ShowRating> getAllRatingsHighToLow(Integer max) throws DAOException;;
	
}
