package com.showflix.app.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showflix.app.dao.IShowDetailsDao;
import com.showflix.app.dao.IShowEntityDao;
import com.showflix.app.dao.entity.Actors;
import com.showflix.app.dao.entity.Awards;
import com.showflix.app.dao.entity.Countries;
import com.showflix.app.dao.entity.Directors;
import com.showflix.app.dao.entity.Genere;
import com.showflix.app.dao.entity.Languages;
import com.showflix.app.dao.entity.ShowDetails;
import com.showflix.app.dao.entity.Writers;
import com.showflix.app.dao.exceptions.DAOException;
import com.showflix.app.dto.Details;
import com.showflix.app.service.IShowService;
import com.showflix.app.service.exceptions.ServiceException;

@Service("showService")
public class ShowServiceImpl implements IShowService {

	@Autowired
	IShowEntityDao showEntityDao;

	@Autowired
	IShowDetailsDao showDetailsDao;

	@Override
	public void updateShowDetails(Details detail) throws ServiceException {
	
		try {
			ShowDetails showDetails = fetchShowDetails(detail);
			showDetailsDao.updateShowDetails(showDetails);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (ParseException e) {
			throw new ServiceException("Format of the show details is not valid ::"+e.getMessage(),e.getCause());
		}
	}

	@Override
	public void insertShowDetails(Details detail) throws ServiceException {
		
		
		try {
			ShowDetails showDetails = fetchShowDetails(detail);
			showDetailsDao.createShowDetails(showDetails);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (ParseException e) {
			throw new ServiceException("Format of the show details is not valid ::"+e.getMessage(),e.getCause());
		}

	}
	
	
	@Override
	public List<ShowDetails> getAllShows() throws ServiceException {
		try {
			return showDetailsDao.findAllShowDetails();
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		}
	}

	@Override
	public List<ShowDetails> getShowByname(String name) throws ServiceException {
		try {
			List<ShowDetails> showDetails =  showDetailsDao.findShowDetailsByName(name);
			 
			if(showDetails!=null){
				return showDetails;
			}
			else{
				throw new ServiceException("No Matches found in Database for the show " + name);
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		}
	}

	@Override
	public ShowDetails getShowById(Integer id) throws ServiceException {
		ShowDetails showDetails =  showDetailsDao.findById(id);
		
		if(showDetails!=null){
			return showDetails;
		}
		throw new ServiceException("No Matches found in Database for show with ID "+ id);
	}

	@Override
	public ShowDetails getShowByImdbId(String imdbId) throws ServiceException {
		try {
			ShowDetails showDetails =  showDetailsDao.findByImdbId(imdbId);
			
			if(showDetails!=null){
				return showDetails;
			}
			throw new ServiceException("No Matches found in Database for show with imdbID "+ imdbId);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		}
	}

	private <T> List<T> fetchEntity(Class<T> entityClass, String entityDetails) {

		String[] details = entityDetails.split(",");
		List<T> entities = new ArrayList<T>();
		for (String str : details) {
			try {
				entities.add(showEntityDao.getEntityByName(entityClass, str.trim(), true));
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return entities;
	}

	private ShowDetails fetchShowDetails(Details detail) throws ParseException {
		ShowDetails sd = new ShowDetails();
		sd.fetch(detail);

		for (Countries country : fetchEntity(Countries.class, detail.getCountry())) {
			country.getShows().add(sd);
			sd.getCountries().add(country);
		}
		for (Actors actor : fetchEntity(Actors.class, detail.getActors())) {
			actor.getShows().add(sd);
			sd.getActors().add(actor);
		}
		for (Awards awd : fetchEntity(Awards.class, detail.getAwards())) {
			awd.getShows().add(sd);
			sd.getAwards().add(awd);
		}
		for (Directors dir : fetchEntity(Directors.class, detail.getDirector())) {
			dir.getShows().add(sd);
			sd.getDirectors().add(dir);
		}
		for (Genere gen : fetchEntity(Genere.class, detail.getGenere())) {
			gen.getShows().add(sd);
			sd.getGeneres().add(gen);
		}
		for (Languages lang : fetchEntity(Languages.class, detail.getLanguage())) {
			lang.getShows().add(sd);
			sd.getLanguages().add(lang);
		}
		for (Writers writ : fetchEntity(Writers.class, detail.getWriter())) {
			writ.getShows().add(sd);
			sd.getWriters().add(writ);
		}

		return sd;

	}

	

}
