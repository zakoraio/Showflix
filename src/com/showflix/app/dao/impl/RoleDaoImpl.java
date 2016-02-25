package com.showflix.app.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.showflix.app.dao.AbstractDao;
import com.showflix.app.dao.IRolesDao;
import com.showflix.app.dao.entity.Role;


@Repository("rolesDao")
public class RoleDaoImpl extends AbstractDao<Integer, Role> implements IRolesDao {

	@Override
	public Role getRoleByName(String name) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("name", name).ignoreCase());
		
		@SuppressWarnings("unchecked")
		List<Role> userList = (List<Role>) criteria.list();
		if(userList.size()>0){
		return userList.get(0);
		}
		return null;
	}

	@Override
	public Role getRoleById(Integer id) {
		return getByKey(id);
	}

}
