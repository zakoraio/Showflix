package com.showflix.app.dao;

import com.showflix.app.dao.exceptions.DAOException;

public interface IShowEntityDao{
	
	public <T> T  getEntityByName(Class<T> entityType , String name , boolean createNew	) throws DAOException;
	
	public <T> T  getEntityById(Class<T> entityType , Integer key) throws DAOException;
	
	public <T> void insertShowEntity(Class<T> entity , Object entityInstance ) throws DAOException;

}
