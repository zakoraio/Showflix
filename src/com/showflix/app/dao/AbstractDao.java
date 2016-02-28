package com.showflix.app.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.showflix.app.dao.exceptions.DAOException;


@Component("abstractDao")
@Scope("prototype")
public class AbstractDao<PK extends Serializable, T> {
	
	private final Class<T> persistentClass;
	
	@SuppressWarnings("unchecked")
	public AbstractDao(){
		this.persistentClass =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}
	
	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public T getByKey(PK key) {
		return (T) getSession().get(persistentClass, key);
	}

	public void persist(T entity) {
		getSession().persist(entity);
	}

	public void save(T entity) throws DAOException{
		getSession().save(entity);
	}
	
	public void update(T entity){
		getSession().update(entity);
	}
	
	public void delete(T entity) {
		getSession().delete(entity);
		
	}
	
	public String getEntityName(){
		return persistentClass.getSimpleName();
	}
	
	public Criteria createEntityCriteria(){
		return getSession().createCriteria(persistentClass);
	}

	
}