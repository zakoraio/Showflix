package com.showflix.app.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.showflix.app.dao.AbstractDao;
import com.showflix.app.dao.IShowDetailsDao;
import com.showflix.app.dao.entity.ShowDetails;
import com.showflix.app.dao.exceptions.DAOException;

@Repository("showDetailsDao")
public class ShowDetailsDaoImpl extends AbstractDao<Integer, ShowDetails> implements IShowDetailsDao {

	public ShowDetails findById(int id) {
		return getByKey(id);
	}

	public ShowDetails deleteShowDetails(ShowDetails showDetails) throws DAOException {
		try{
		delete(showDetails);
		return showDetails;
		}
		catch(Exception e){
			throw new DAOException("Database Error @ ShowDetailsDaoImpl ::" + e.getMessage(), e.getCause());
		}
	}

	public List<ShowDetails> findShowDetailsByName(String movieName) throws DAOException {
		try{
			Query query  = getSession().createQuery("from "+getEntityName()+ " where lower(title) like :movieName");
			query.setString("movieName", '%'+movieName.toLowerCase()+'%');

			@SuppressWarnings("unchecked")
			List<ShowDetails> showList = (List<ShowDetails>) query.list();

			return showList;
		}
		catch(Exception e){
			throw new DAOException("Database Error @ ShowDetailsDaoImpl ::" + e.getMessage(), e.getCause());
		}
		

	}

	@Override
	public ShowDetails findByImdbId(String imDbId) throws DAOException {
		try {
			Query query  = getSession().createQuery("from "+getEntityName()+ " where imdbId = :imdbID");
			query.setString("imdbID", imDbId);

			@SuppressWarnings("unchecked")
			List<ShowDetails> showList = (List<ShowDetails>) query.list();
			if(showList.size()>0){
			return showList.get(0);
			}
			return null;
			
		} catch (Exception e) {
			throw new DAOException("Database Error @ ShowDetailsDaoImpl ::" + e.getMessage(), e.getCause());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShowDetails> findAllShowDetails() throws DAOException {
		try {
			Query query  = getSession().createQuery("from "+getEntityName());
			return (List<ShowDetails>) query.list();
		} catch (Exception e) {
			throw new DAOException("Database Error @ ShowDetailsDaoImpl ::" + e.getMessage(), e.getCause());
		}
	}

	@Override
	public void createShowDetails(ShowDetails showDetails) throws DAOException {
		try {
			persist(showDetails);
		} catch (Exception e) {
			throw new DAOException("Database Error @ ShowDetailsDaoImpl ::" + e.getMessage(), e.getCause());
		}

	}

	@Override
	public void updateShowDetails(ShowDetails showDetails) throws DAOException {
		try {
			update(showDetails);
		} catch (Exception e) {
			throw new DAOException("Database Error @ ShowDetailsDaoImpl ::" + e.getMessage(), e.getCause());
		}
	}

}
