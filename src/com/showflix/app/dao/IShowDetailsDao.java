package com.showflix.app.dao;

import java.util.List;

import com.showflix.app.dao.entity.ShowDetails;

public interface IShowDetailsDao {


	ShowDetails findById(int id);
	
	ShowDetails findByImdbId(String imdbId);

	boolean createShowDetails(ShowDetails showDetails);
	
	boolean updateShowDetails(ShowDetails showDetails);
	
	ShowDetails deleteShowDetails(ShowDetails showDetails);
	
	List<ShowDetails> findAllShowDetails();

	List<ShowDetails> findShowDetailsByName(String name);
	
}
