package com.showflix.app.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.showflix.app.dao.AbstractDao;
import com.showflix.app.dao.IRatingDao;
import com.showflix.app.dao.entity.ShowRating;
import com.showflix.app.dao.exceptions.DAOException;


@Repository("ratingDao")
public class RatingDaoImpl extends AbstractDao<Integer, ShowRating> implements IRatingDao {

	@Override
	public void createRating(ShowRating rating) throws DAOException {
		try {
			persist(rating);
		} catch (Exception e) {
			throw new DAOException("Database Error @ RatingDaoImpl ::" + e.getMessage(), e.getCause());
		}
	}

	@Override
	public List<ShowRating> getAllRatingForShow(String imdbID) throws DAOException {
		try {
			Query query = getSession().createQuery("from " + getEntityName() + " where imdbId = :imdbID");
			query.setString("imdbID", imdbID);

			@SuppressWarnings("unchecked")
			List<ShowRating> ratingList = (List<ShowRating>) query.list();
			return ratingList;
		} catch (Exception e) {
			throw new DAOException("Database Error @ RatingDaoImpl ::" + e.getMessage(), e.getCause());
		}
	}

	@Override
	public void updateRating(ShowRating rating) throws DAOException {
		try {
			update(rating);
		} catch (Exception e) {
			throw new DAOException("Database Error @ RatingDaoImpl ::" + e.getMessage(), e.getCause());
		}
	}

	@Override
	public ShowRating getRatingForShowByUser(String imdbId, String userName) throws DAOException {

		Query query = getSession()
				.createQuery("from " + getEntityName() + " where imdbId = :imdbID and userName = :userName");
		query.setString("imdbID", imdbId);
		query.setString("userName", userName);
		try {
			@SuppressWarnings("unchecked")
			List<ShowRating> ratingList = (List<ShowRating>) query.list();
			if (ratingList.size() > 0) {
				return ratingList.get(0);
			}
			return null;
		} catch (Exception e) {
			throw new DAOException("Database Error @ RatingDaoImpl ::" + e.getMessage(), e.getCause());
		}
	}

}
