package com.showflix.app.service;

import java.util.List;

import com.showflix.app.controller.exception.ShowDetailsNotFoundException;
import com.showflix.app.dto.Details;
import com.showflix.app.service.exceptions.ServiceException;

public interface IShowService {

	public void updateShowDetails(Details detail) throws ServiceException, ShowDetailsNotFoundException ;
	
	public void insertShowDetails(Details detail) throws ServiceException;
	
	public List<Details> getAllShows() throws ServiceException;
	
	public List<Details> getShowByname(String name) throws ServiceException;
	
	public Details getShowById(Integer id) throws ServiceException;
	
	public Details getShowByImdbId(String imdbId) throws ServiceException;
	
	public void deleteShow(String imdbId) throws ShowDetailsNotFoundException, ServiceException;
	
	public List<Details> getTopRatedShowsByImdbRating(Integer max) throws ShowDetailsNotFoundException, ServiceException;

	List<Details> getTopRatedShowsByShowFlixRating(Integer max) throws ShowDetailsNotFoundException, ServiceException;

	
}
