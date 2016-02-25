package com.showflix.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showflix.app.dao.IRolesDao;
import com.showflix.app.dao.IUserDao;
import com.showflix.app.dao.entity.Role;
import com.showflix.app.dao.entity.User;
import com.showflix.app.dao.exceptions.DAOException;
import com.showflix.app.service.IUserService;
import com.showflix.app.service.exceptions.ServiceException;


@Service("userService")
public class UserServiceImpl implements IUserService {
	
	@Autowired
	IUserDao userDao;
	
	@Autowired
	IRolesDao roleDao;

	@Override
	public User getUserbyId(Integer id) throws ServiceException {
		try {
			User user  = userDao.getUserById(id);
			if(user!=null){
				return user;
			}
			else{
				throw new ServiceException("No matches foound for the user with id "+ id);
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		}
	}

	@Override
	public void addUser(User user) throws ServiceException {
		try {
			userDao.save(user);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		}

	}


	@Override
	public void assignRoleToExistingUser(String userName, String roleName) throws ServiceException {

		Role roleObj = getRoleFromDb(roleName);
		User userObj;
		try {
			userObj = userDao.getUserByUserName(userName);
			if(userObj!=null){
				userObj.getRoles().add(roleObj);
				roleObj.getUsers().add(userObj);
				userDao.save(userObj);
			}
			else{
				throw new ServiceException("User Not Found");
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		}
		
		

	}

	@Override
	public User getUserbyUserName(String userName) throws ServiceException {
		try {
			User user =  userDao.getUserByUserName(userName);
			if(user!=null){
				return user;
			}
			else{
				throw new ServiceException("No matches foound for the user with username "+ userName);
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		}
	}

	@Override
	public List<User> getUserByFirstName(String name) throws ServiceException {
		try {
			List<User>  users = userDao.getUserByName(name);
			if(users!=null){
				return users;
			}
			else {
				throw new ServiceException("No matches foound for the user with firstname "+ name);
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		}
	}

	@Override
	public void assignRoleToNewUser(User user, String roleName) throws ServiceException {
		
		try {
			Role roleObj = getRoleFromDb(roleName);
			User userObj = userDao.getUserByUserName(user.getUserName());
			if(userObj==null){
				userObj = new User();
				userObj.getRoles().add(roleObj);
				roleObj.getUsers().add(userObj);
				userDao.save(userObj);
			}
			else{
				
			}
			
		} catch (DAOException e) {
			throw new ServiceException("User Already Exists in Db");
		}
		
	}

	
	private Role getRoleFromDb(String roleName){
		Role roleObj = roleDao.getRoleByName(roleName);
		if(roleObj == null){
			roleObj = new Role();
			roleObj.setName(roleName);
		}
		
		return roleObj;
	}
}
