package com.showflix.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showflix.app.dao.IRolesDao;
import com.showflix.app.dao.IUserDao;
import com.showflix.app.dao.entity.Role;
import com.showflix.app.dao.entity.User;
import com.showflix.app.service.IUserService;


@Service("userService")
public class UserServiceImpl implements IUserService {
	
	@Autowired
	IUserDao userDao;
	
	@Autowired
	IRolesDao roleDao;

	@Override
	public User getUserbyId(Integer id) {
		return userDao.getUserById(id);
	}

	@Override
	public void addUser(User user) {
		userDao.save(user);

	}


	@Override
	public void assignRoleToExistingUser(String userName, String roleName) {

		Role roleObj = getRoleFromDb(roleName);
		User userObj = userDao.getUserByUserName(userName);
		if(userObj!=null){
			userObj.getRoles().add(roleObj);
			roleObj.getUsers().add(userObj);
		}
		userDao.save(userObj);
		

	}

	@Override
	public User getUserbyUserName(String userName) {
		return userDao.getUserByUserName(userName);
	}

	@Override
	public List<User> getUserByFirstName(String name) {
		return userDao.getUserByName(name);
	}

	@Override
	public void assignRoleToNewUser(User user, String roleName) {
		Role roleObj = getRoleFromDb(roleName);
		User userObj = userDao.getUserByUserName(user.getUserName());
		if(userObj==null){
			userObj = new User();
			userObj.getRoles().add(roleObj);
			roleObj.getUsers().add(userObj);
		}
		userDao.save(userObj);
		
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
