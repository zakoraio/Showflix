package com.showflix.app.service;

import java.text.ParseException;
import java.util.List;

import com.showflix.app.dao.entity.ShowDetails;
import com.showflix.app.dto.Details;

public interface IShowService {

	public void updateShowDetails(Details detail) throws ParseException;
	
	public void insertShowDetails(Details detail) throws ParseException;
	
	public List<ShowDetails> getAllShows();
	
	public List<ShowDetails> getShowByname(String name);
	
	public ShowDetails getShowById(Integer id);
	
	public ShowDetails getShowByImdbId(String imdbId);
	
}
