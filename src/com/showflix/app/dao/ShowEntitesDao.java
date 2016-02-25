package com.showflix.app.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository("showEntitesDao")
@Scope("prototype")
public class ShowEntitesDao< T> {

	
	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}
		
	@SuppressWarnings("unchecked")
	public T getById(Integer key,String className) {
		Query query = getSession().createQuery(" from "+className+" where id = ?");
		query.setInteger(0, key);
		return (T) query.list().get(0);
	}

	@SuppressWarnings("unchecked")
	public T getByName(String name,String className) {
		
		Query query = getSession().createQuery(" from "+className+" where name like ?");
		query.setString(0, "%"+name+"%");
		if (query.list().size() > 0) {
			T entity = (T) query.list().get(0);
			return entity;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public void insert(Object entityInstance){
		getSession().persist((T)entityInstance);
	}
	
}
