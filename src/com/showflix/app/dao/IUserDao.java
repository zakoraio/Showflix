package com.showflix.app.dao;

import java.util.List;

import com.showflix.app.dao.entity.User;
import com.showflix.app.dao.exceptions.DAOException;

public interface IUserDao {
	
	public void save(User user) throws DAOException;
	
	public List<User> getUserByName(String name) throws DAOException;
	
	public User getUserByUserName(String userName) throws DAOException;
	
	public User getUserById(Integer id) throws DAOException;
	
	public void deleteUser(User user) throws DAOException;

}
