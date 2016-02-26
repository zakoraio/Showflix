package com.showflix.app.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.showflix.app.dao.AbstractDao;
import com.showflix.app.dao.IRatingDao;
import com.showflix.app.dao.entity.ShowRating;
import com.showflix.app.dao.exceptions.DAOException;

public class RatingDaoImpl extends AbstractDao<Integer,ShowRating > implements IRatingDao {

	@Override
	public void createRating(ShowRating rating) throws DAOException {
		try{
		persist(rating);
		}
		catch(Exception e){
			throw new DAOException("Database Error @ RatingDaoImpl ::" + e.getMessage(), e.getCause());
		}
	}

	@Override
	public List<ShowRating> getAllRatingForShow(String imdbID) throws DAOException {
		try {
			Criteria criteria = createEntityCriteria();
			criteria.add(Restrictions.eq("imdbId", imdbID));

			@SuppressWarnings("unchecked")
			List<ShowRating> ratingList = (List<ShowRating>) criteria.list();
			return ratingList;
		} catch (Exception e) {
			throw new DAOException("Database Error @ RatingDaoImpl ::" + e.getMessage(), e.getCause());
		}
	}

	@Override
	public void updateRating(ShowRating rating) throws DAOException {
		try{
		update(rating);
		}
		catch(Exception e){
			throw new DAOException("Database Error @ RatingDaoImpl ::" + e.getMessage(), e.getCause());
		}
	}

}
