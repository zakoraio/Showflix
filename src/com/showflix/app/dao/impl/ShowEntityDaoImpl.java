package com.showflix.app.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.showflix.app.dao.IShowEntityDao;
import com.showflix.app.dao.ShowEntitesDao;
import com.showflix.app.dao.entity.Actors;
import com.showflix.app.dao.entity.Awards;
import com.showflix.app.dao.entity.Countries;
import com.showflix.app.dao.entity.Directors;
import com.showflix.app.dao.entity.Genere;
import com.showflix.app.dao.entity.Languages;
import com.showflix.app.dao.entity.Writers;
import com.showflix.app.dao.exceptions.DAOException;

@Repository("showEntityDaoImpl")
public class ShowEntityDaoImpl implements IShowEntityDao {

	@Autowired
	ShowEntitesDao<Actors> actorsDao;

	@Autowired
	ShowEntitesDao<Awards> awardsDao;

	@Autowired
	ShowEntitesDao<Countries> countriesDao;

	@Autowired
	ShowEntitesDao<Genere> generesDao;

	@Autowired
	ShowEntitesDao<Languages> languagesDao;

	@Autowired
	ShowEntitesDao<Writers> writersDao;

	@Autowired
	ShowEntitesDao<Directors> directorDao;

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getEntityByName(Class<T> entityClass, String name, boolean createNew) throws DAOException {
		T t = (T) getEntityDao(entityClass).getByName(name, entityClass.getSimpleName());
		if (t == null && createNew) {
			Method m;
			try {
				t = (T) (entityClass.newInstance());
				m = entityClass.getMethod("setName", String.class);
				m.invoke(t, name);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new DAOException("Database Error @ ShowEntityDaoImpl :" + e.getMessage(), e.getCause());
			} catch (IllegalAccessException e) {
				throw new DAOException("Database Error @ ShowEntityDaoImpl :" + e.getMessage(), e.getCause());
			} catch (IllegalArgumentException e) {
				throw new DAOException("Database Error @ ShowEntityDaoImpl :" + e.getMessage(), e.getCause());
			} catch (InvocationTargetException e) {
				throw new DAOException("Database Error @ ShowEntityDaoImpl :" + e.getMessage(), e.getCause());
			} catch (InstantiationException e) {
				throw new DAOException("Database Error @ ShowEntityDaoImpl :" + e.getMessage(), e.getCause());
			}

		}

		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getEntityById(Class<T> entityClass, Integer key) throws DAOException {
		try {
			return (T) getEntityDao(entityClass).getById(key, entityClass.getSimpleName());
		} catch (Exception e) {
			throw new DAOException("Database Error @ ShowEntityDaoImpl :" + e.getMessage(), e.getCause());
		}
	}

	@Override
	public <T> void insertShowEntity(Class<T> entityClass, Object entityInstance) throws DAOException {
		try {
			getEntityDao(entityClass).insert(entityInstance);
		} catch (Exception e) {
			throw new DAOException("Database Error @ ShowEntityDaoImpl :" + e.getMessage(), e.getCause());
		}

	}

	private <T> ShowEntitesDao<?> getEntityDao(Class<T> entityClass) {

		if (entityClass.equals(Actors.class)) {
			return actorsDao;
		} else if (entityClass.equals(Awards.class)) {
			return awardsDao;
		} else if (entityClass.equals(Countries.class)) {
			return countriesDao;
		} else if (entityClass.equals(Genere.class)) {
			return generesDao;
		} else if (entityClass.equals(Languages.class)) {
			return languagesDao;
		} else if (entityClass.equals(Writers.class)) {
			return writersDao;
		} else if (entityClass.equals(Directors.class)) {
			return directorDao;
		}

		return null;
	}

}
