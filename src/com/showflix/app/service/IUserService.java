package com.showflix.app.service;

import java.util.List;

import com.showflix.app.dao.entity.User;

public interface IUserService {
	
	public User getUserbyId(Integer id);
	
	public User getUserbyUserName(String userName);
	
	public void addUser(User user);
	
	public List<User> getUserByFirstName(String name);
	
	public void assignRoleToExistingUser(String userName , String roleName);
	
	public void assignRoleToNewUser(User user , String roleName);

}
