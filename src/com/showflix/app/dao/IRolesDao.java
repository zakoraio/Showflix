package com.showflix.app.dao;

import com.showflix.app.dao.entity.Role;

public interface IRolesDao {
	
	public Role getRoleByName(String name);
	
	public Role getRoleById(Integer id);

}
