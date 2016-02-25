package com.showflix.app.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.showflix.app.dao.AbstractDao;
import com.showflix.app.dao.IShowDetailsDao;
import com.showflix.app.dao.entity.ShowDetails;

@Repository("showDetailsDao")
public class ShowDetailsDaoImpl extends AbstractDao<Integer, ShowDetails> implements IShowDetailsDao {
	
	
	
	public ShowDetails findById(int id) {
		return getByKey(id);
	}


	public ShowDetails deleteShowDetails(ShowDetails showDetails) {
		delete(showDetails);
		return showDetails;
	}

	public List<ShowDetails> findShowDetailsByName(String name) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("name", name).ignoreCase());
		
		@SuppressWarnings("unchecked")
		List<ShowDetails> showList = (List<ShowDetails>) criteria.list();
		
		return showList;
		
	}

	@Override
	public ShowDetails findByImdbId(String imDbId) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("imdbID", imDbId));
		
		@SuppressWarnings("unchecked")
		List<ShowDetails> showList = (List<ShowDetails>) criteria.list();
		return showList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShowDetails> findAllShowDetails() {
		Criteria criteria = createEntityCriteria();
		return (List<ShowDetails>) criteria.list();
	}

	@Override
	public boolean createShowDetails(ShowDetails showDetails) {
		try{
			persist(showDetails);
			return true;
			} catch(Exception e){
				e.printStackTrace();
			}
			return false;
		
	}


	@Override
	public boolean updateShowDetails(ShowDetails showDetails) {
		try{
		update(showDetails);
		return true;
		} catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
}
