package com.showflix.app.service;

import java.util.List;

import com.showflix.app.controller.exception.UserAlreadyExistsException;
import com.showflix.app.controller.exception.UserNotFoundException;
import com.showflix.app.dao.entity.User;
import com.showflix.app.service.exceptions.ServiceException;

public interface IUserService {
	
	public User getUserbyId(Integer id)throws ServiceException, UserNotFoundException;
	
	public User getUserbyUserName(String userName)throws ServiceException, UserNotFoundException;
	
	public void addUser(User user)throws ServiceException, UserAlreadyExistsException;
	
	public List<User> getUserByFirstName(String name) throws ServiceException;
	
	public void assignRoleToExistingUser(String userName , String roleName)throws ServiceException, UserNotFoundException;
	
	public void assignRoleToNewUser(User user , String roleName)throws ServiceException, UserAlreadyExistsException;
	
	public User updateUser(User user) throws ServiceException;
	
	public User deleteUser(String userName) throws ServiceException;

}
