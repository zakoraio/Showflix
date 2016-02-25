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
	public <T> T getEntityByName(Class<T> entityClass, String name , boolean createNew) {
			T t = (T) getEntityDao(entityClass).getByName(name,entityClass.getSimpleName());
			if(t == null && createNew){
				Method m;
				try {
					t = (T)(entityClass.newInstance());
					m = entityClass.getMethod("setName", String.class);
					m.invoke(t, name);
				} catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
	
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getEntityById(Class<T> entityClass, Integer key) {
		return (T) getEntityDao(entityClass).getById(key,entityClass.getSimpleName());
	}
	
	@Override
	public <T> void insertShowEntity(Class<T> entityClass, Object entityInstance) {
		getEntityDao(entityClass).insert(entityInstance);
		
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
