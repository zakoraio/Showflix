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
import com.showflix.app.dto.Details;
import com.showflix.app.service.IShowService;

@Service("showService")
public class ShowServiceImpl implements IShowService {

	@Autowired
	IShowEntityDao showEntityDao;

	@Autowired
	IShowDetailsDao showDetailsDao;

	@Override
	public void updateShowDetails(Details detail) throws ParseException {
	
		ShowDetails showDetails = fetchShowDetails(detail);
		showDetailsDao.updateShowDetails(showDetails);
	}

	@Override
	public void insertShowDetails(Details detail) throws ParseException {
		
		ShowDetails showDetails = fetchShowDetails(detail);
		showDetailsDao.createShowDetails(showDetails);

	}
	
	
	@Override
	public List<ShowDetails> getAllShows() {
		return showDetailsDao.findAllShowDetails();
	}

	@Override
	public List<ShowDetails> getShowByname(String name) {
		return showDetailsDao.findShowDetailsByName(name);
	}

	@Override
	public ShowDetails getShowById(Integer id) {
		return showDetailsDao.findById(id);
	}

	@Override
	public ShowDetails getShowByImdbId(String imdbId) {
		return showDetailsDao.findByImdbId(imdbId);
	}

	private <T> List<T> fetchEntity(Class<T> entityClass, String entityDetails) {

		String[] details = entityDetails.split(",");
		List<T> entities = new ArrayList<T>();
		for (String str : details) {
			entities.add(showEntityDao.getEntityByName(entityClass, str.trim(), true));
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
