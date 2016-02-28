package com.showflix.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.showflix.app.dao.IRatingDao;
import com.showflix.app.dao.IShowDetailsDao;
import com.showflix.app.dao.entity.ShowRating;
import com.showflix.app.dao.exceptions.DAOException;
import com.showflix.app.service.IRatingService;
import com.showflix.app.service.exceptions.ServiceException;


@Transactional
@Service("ratingService")
public class RatingServiceImpl implements IRatingService {

	@Autowired
	IRatingDao ratingDao;

	@Autowired
	IShowDetailsDao showDetailsDao;

	

	@Override
	public void addRating(ShowRating rating) throws ServiceException {
		
		try {
			
			ShowRating existingRating = ratingDao.getRatingForShowByUser(rating.getImdbId(), rating.getUserName());
			if(existingRating!=null){
				existingRating.setRating(rating.getRating());
				ratingDao.updateRating(existingRating);
			}
			
			ratingDao.createRating(rating);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public double getAvgRatingForShow(String imdbId) throws ServiceException {
		try {
		
				List<ShowRating> ratings = ratingDao.getAllRatingForShow(imdbId);

				double avgRating = 0;

				for (ShowRating rating : ratings) {
					avgRating += rating.getRating();
				}

				if (avgRating > 0) {
					avgRating /= ratings.size();
				}

				return avgRating;
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e.getCause());
		}
	}

}
