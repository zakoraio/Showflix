package com.showflix.app.dao;

public interface IShowEntityDao{
	
	public <T> T  getEntityByName(Class<T> entityType , String name , boolean createNew	);
	
	public <T> T  getEntityById(Class<T> entityType , Integer key);
	
	public <T> void insertShowEntity(Class<T> entity , Object entityInstance );

}
