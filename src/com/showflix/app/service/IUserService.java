package com.showflix.app.service;

import java.util.List;

import com.showflix.app.dao.entity.User;
import com.showflix.app.service.exceptions.ServiceException;

public interface IUserService {
	
	public User getUserbyId(Integer id)throws ServiceException;
	
	public User getUserbyUserName(String userName)throws ServiceException;
	
	public void addUser(User user)throws ServiceException;
	
	public List<User> getUserByFirstName(String name) throws ServiceException;
	
	public void assignRoleToExistingUser(String userName , String roleName)throws ServiceException;
	
	public void assignRoleToNewUser(User user , String roleName)throws ServiceException;

}
