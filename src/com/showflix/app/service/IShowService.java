package com.showflix.app.service;

import java.util.List;

import com.showflix.app.dao.entity.ShowDetails;
import com.showflix.app.dto.Details;
import com.showflix.app.service.exceptions.ServiceException;

public interface IShowService {

	public void updateShowDetails(Details detail) throws ServiceException ;
	
	public void insertShowDetails(Details detail) throws ServiceException;
	
	public List<ShowDetails> getAllShows() throws ServiceException;
	
	public List<ShowDetails> getShowByname(String name) throws ServiceException;
	
	public ShowDetails getShowById(Integer id) throws ServiceException;
	
	public ShowDetails getShowByImdbId(String imdbId) throws ServiceException;
	
}
