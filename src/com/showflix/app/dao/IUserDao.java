package com.showflix.app.dao;

import java.util.List;

import com.showflix.app.dao.entity.User;

public interface IUserDao {
	
	public void save(User user);
	
	public List<User> getUserByName(String name);
	
	public User getUserByUserName(String userName);
	
	public User getUserById(Integer id);
	
	public void deleteUser(User user);

}
