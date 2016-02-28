package com.showflix.app.dao;

import java.util.List;

import com.showflix.app.dao.entity.ShowDetails;
import com.showflix.app.dao.exceptions.DAOException;

public interface IShowDetailsDao {


	ShowDetails findById(int id);
	
	ShowDetails findByImdbId(String imdbId) throws DAOException;

	void createShowDetails(ShowDetails showDetails) throws DAOException;
	
	void updateShowDetails(ShowDetails showDetails) throws DAOException;
	
	ShowDetails deleteShowDetails(ShowDetails showDetails) throws DAOException;
	
	List<ShowDetails> findAllShowDetails() throws DAOException;

	List<ShowDetails> findShowDetailsByName(String name) throws DAOException;

	List<ShowDetails> getTopRatedShowsByImdbRating(Integer max) throws DAOException;

	List<ShowDetails> getTopRatedShowsByShowFlixRating(Integer max) throws DAOException;
	
}
