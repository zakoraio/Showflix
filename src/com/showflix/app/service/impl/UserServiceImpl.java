package com.showflix.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.showflix.app.controller.exception.UserAlreadyExistsException;
import com.showflix.app.controller.exception.UserNotFoundException;
import com.showflix.app.dao.IRolesDao;
import com.showflix.app.dao.IUserDao;
import com.showflix.app.dao.entity.Role;
import com.showflix.app.dao.entity.User;
import com.showflix.app.dao.exceptions.DAOException;
import com.showflix.app.service.IUserService;
import com.showflix.app.service.exceptions.ServiceException;

@Transactional
@Service("userService")
public class UserServiceImpl implements IUserService {

	@Autowired
	IUserDao userDao;

	@Autowired
	IRolesDao roleDao;

	@Override
	public User getUserbyId(Integer id) throws ServiceException, UserNotFoundException {
		try {
			User user = userDao.getUserById(id);
			if (user != null) {
				return user;
			} else {
				throw new UserNotFoundException();
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public void addUser(User user) throws ServiceException, UserAlreadyExistsException {
		assignRoleToNewUser(user,"user");

	}

	@Override
	public void assignRoleToExistingUser(String userName, String roleName) throws ServiceException, UserNotFoundException {

		Role roleObj = getRoleFromDb(roleName);
		User userObj;
		try {
			userObj = userDao.getUserByUserName(userName);
			if (userObj != null) {
				userObj.getRoles().add(roleObj);
				roleObj.getUsers().add(userObj);
				userDao.save(userObj);
			} else {
				 throw new UserNotFoundException();
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e.getCause());
		}

	}

	@Override
	public User getUserbyUserName(String userName) throws ServiceException, UserNotFoundException {
		try {
			User user = userDao.getUserByUserName(userName);
			if (user != null) {
				return user;
			} else {
					throw new UserNotFoundException();
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public List<User> getUserByFirstName(String name) throws ServiceException {
		try {
			List<User> users = userDao.getUserByName(name);
			if (users != null) {
				return users;
			} else {
				throw new ServiceException("No matches foound for the user with firstname " + name);
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public void assignRoleToNewUser(User user, String roleName) throws ServiceException, UserAlreadyExistsException {

		try {
			Role roleObj = getRoleFromDb(roleName);
			User userObj = userDao.getUserByUserName(user.getUserName());
			if (userObj == null) {
				user.getRoles().add(roleObj);
				roleObj.getUsers().add(user);
				userDao.save(user);
			} else {
				throw new UserAlreadyExistsException();
			}

		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		}

	}

	private Role getRoleFromDb(String roleName) {
		Role roleObj = roleDao.getRoleByName(roleName);
		if (roleObj == null) {
			roleObj = new Role();
			roleObj.setName(roleName);
		}
		return roleObj;
	}

	@Override
	public User updateUser(User user) throws ServiceException {
		try {
			User existingUser = userDao.getUserByUserName(user.getUserName());
			if (existingUser == null) {
				return null;
			}
			existingUser.fetch(user);
			userDao.updateUser(existingUser);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e.getCause());
		}
		return user;

	}

	@Override
	public User deleteUser(String userName) throws ServiceException {
		User existingUser = null;
		try {
			existingUser = userDao.getUserByUserName(userName);
			if(existingUser == null){
				return null;
			}
			userDao.deleteUser(existingUser);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e.getCause());
		}
		return existingUser;

	}
}
