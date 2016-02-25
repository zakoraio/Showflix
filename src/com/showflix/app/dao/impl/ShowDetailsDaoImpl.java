package com.showflix.app.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
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

	public List<ShowDetails> findShowDetailsByName(String name) throws DAOException {
		try{
			Criteria criteria = createEntityCriteria();
			criteria.add(Restrictions.eq("name", name).ignoreCase());

			@SuppressWarnings("unchecked")
			List<ShowDetails> showList = (List<ShowDetails>) criteria.list();

			return showList;
		}
		catch(Exception e){
			throw new DAOException("Database Error @ ShowDetailsDaoImpl ::" + e.getMessage(), e.getCause());
		}
		

	}

	@Override
	public ShowDetails findByImdbId(String imDbId) throws DAOException {
		try {
			Criteria criteria = createEntityCriteria();
			criteria.add(Restrictions.eq("imdbID", imDbId));

			@SuppressWarnings("unchecked")
			List<ShowDetails> showList = (List<ShowDetails>) criteria.list();
			return showList.get(0);
		} catch (Exception e) {
			throw new DAOException("Database Error @ ShowDetailsDaoImpl ::" + e.getMessage(), e.getCause());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShowDetails> findAllShowDetails() throws DAOException {
		try {
			Criteria criteria = createEntityCriteria();
			return (List<ShowDetails>) criteria.list();
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
