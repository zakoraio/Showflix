package com.showflix.app.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.showflix.app.controller.exception.ShowDetailsNotFoundException;
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

@Transactional
@Service("showService")
public class ShowServiceImpl implements IShowService {

	@Autowired
	IShowEntityDao showEntityDao;

	@Autowired
	IShowDetailsDao showDetailsDao;

	@Override
	public void updateShowDetails(Details detail) throws ServiceException, ShowDetailsNotFoundException {
		try {
			ShowDetails existingShowDetails = showDetailsDao.findByImdbId(detail.getImdbID());
			if (existingShowDetails == null) {
				throw new ShowDetailsNotFoundException();
			}
			showDetailsDao.deleteShowDetails(existingShowDetails);
			ShowDetails showDetails = fetchShowDetails(detail);
			showDetailsDao.createShowDetails(showDetails);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e.getCause());
		} catch (ParseException e) {
			throw new ServiceException("Format of the show details is not valid ::" + e.getMessage(), e.getCause());
		}
	}

	@Override
	public void insertShowDetails(Details detail) throws ServiceException {

		try {
			ShowDetails showDetails = fetchShowDetails(detail);
			showDetailsDao.createShowDetails(showDetails);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e.getCause());
		} catch (ParseException e) {
			throw new ServiceException("Format of the show details is not valid ::" + e.getMessage(), e.getCause());
		}

	}

	@Override
	public List<Details> getAllShows() throws ServiceException {
		List<Details> details = null;
		try {
			List<ShowDetails> showDetailList = showDetailsDao.findAllShowDetails();
			if (showDetailList.size() == 0) {
				return null;
			}
			details = new ArrayList<Details>();
			for (ShowDetails sd : showDetailList) {
				Details detail = new Details();
				detail.fetch(sd);
				details.add(detail);
			}

		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e.getCause());
		}
		return details;
	}

	@Override
	public List<Details> getShowByname(String name) throws ServiceException {

		List<Details> details = null;
		try {
			List<ShowDetails> showDetailList = showDetailsDao.findShowDetailsByName(name);
			if (showDetailList.size() == 0) {
				return null;
			}
			details = new ArrayList<Details>();
			for (ShowDetails sd : showDetailList) {
				Details detail = new Details();
				detail.fetch(sd);
				details.add(detail);
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e.getCause());
		}
		return details;
	}

	@Override
	public Details getShowById(Integer id) throws ServiceException {
		ShowDetails showDetails = showDetailsDao.findById(id);

		if (showDetails == null) {
			return null;
		}
		Details detail = new Details();
		detail.fetch(showDetails);

		return detail;
	}

	@Override
	public Details getShowByImdbId(String imdbId) throws ServiceException {
		Details detail = null;
		try {
			ShowDetails showDetails = showDetailsDao.findByImdbId(imdbId);

			if (showDetails == null) {
				return null;
			}
			detail = new Details();
			detail.fetch(showDetails);

		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e.getCause());
		}
		return detail;
	}
	
	private <T> List<T> fetchEntity(Class<T> entityClass, String entityDetails) throws ServiceException {

		String[] details = entityDetails.split(",");
		List<T> entities = new ArrayList<T>();
		for (String str : details) {
			try {
				entities.add(showEntityDao.getEntityByName(entityClass, str.trim(), true));
			} catch (DAOException e) {
				throw new ServiceException(e.getMessage(), e.getCause());
			}
		}

		return entities;
	}

	private ShowDetails fetchShowDetails(Details detail) throws ParseException, ServiceException {
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
		for (Genere gen : fetchEntity(Genere.class, detail.getGenre())) {
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

	@Override
	public void deleteShow(String imdbId) throws ShowDetailsNotFoundException, ServiceException {
		// TODO Auto-generated method stub
		try {
			ShowDetails existingShowDetails = showDetailsDao.findByImdbId(imdbId);
			if (existingShowDetails == null) {
				throw new ShowDetailsNotFoundException();
			}
			showDetailsDao.deleteShowDetails(existingShowDetails);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e.getCause());

		}
	}

	@Override
	public List<Details> getTopRatedShowsByImdbRating(Integer max) throws ShowDetailsNotFoundException, ServiceException {

		List<Details> details = null;
		try {
			List<ShowDetails> showDetailList = showDetailsDao.getTopRatedShowsByImdbRating(max);
			if (showDetailList.size() == 0) {
				return null;
			}
			details = new ArrayList<Details>();
			for (ShowDetails sd : showDetailList) {
				Details detail = new Details();
				detail.fetch(sd);
				details.add(detail);
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e.getCause());
		}
		return details;
	}
	
	@Override
	public List<Details> getTopRatedShowsByShowFlixRating(Integer max) throws ShowDetailsNotFoundException, ServiceException {

		List<Details> details = null;
		try {
			List<ShowDetails> showDetailList = showDetailsDao.getTopRatedShowsByShowFlixRating(max);
			if (showDetailList.size() == 0) {
				return null;
			}
			details = new ArrayList<Details>();
			for (ShowDetails sd : showDetailList) {
				Details detail = new Details();
				detail.fetch(sd);
				details.add(detail);
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e.getCause());
		}
		return details;
	}

}
