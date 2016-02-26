package com.showflix.app.service.impl;

import java.util.List;

import com.showflix.app.dao.IRatingDao;
import com.showflix.app.dao.IShowDetailsDao;
import com.showflix.app.dao.entity.ShowDetails;
import com.showflix.app.dao.entity.ShowRating;
import com.showflix.app.dao.exceptions.DAOException;
import com.showflix.app.service.IRatingService;
import com.showflix.app.service.exceptions.ServiceException;

public class RatingServiceImpl implements IRatingService {

	IRatingDao ratingDao;

	IShowDetailsDao showDetailsDao;

	@Override
	public void updateRating(ShowRating rating) throws ServiceException {
		try {
			ratingDao.updateRating(rating);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public void addRating(ShowRating rating) throws ServiceException {
		try {
			ratingDao.createRating(rating);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public double getAvgRatingForShow(String imdbId) throws ServiceException {
		try {
			ShowDetails showDetails = showDetailsDao.findByImdbId(imdbId);
			if (showDetails == null) {
				throw new ServiceException("The show with ImdbId " + imdbId + " was not found");
			} else {
				List<ShowRating> ratings = ratingDao.getAllRatingForShow(imdbId);

				double avgRating = -1;

				for (ShowRating rating : ratings) {
					avgRating += rating.getRating();
				}

				if (avgRating > 0) {
					avgRating /= ratings.size();
				}

				return avgRating;
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e.getCause());
		}
	}

}
