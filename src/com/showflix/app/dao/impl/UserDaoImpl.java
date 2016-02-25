package com.showflix.app.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.showflix.app.dao.AbstractDao;
import com.showflix.app.dao.IUserDao;
import com.showflix.app.dao.entity.User;


@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements IUserDao {

	@Override
	public void save(User user) {
		persist(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserByName(String name) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("firstName", name).ignoreCase());
		return (List<User>) criteria.list();
	}

	@Override
	public void deleteUser(User user) {
		delete(user);

	}

	@Override
	public User getUserById(Integer id) {
		return getByKey(id);
	}

	@Override
	public User getUserByUserName(String userName) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("userName", userName).ignoreCase());

		@SuppressWarnings("unchecked")
		List<User> userList = (List<User>) criteria.list();
		if (userList.size() > 0) {
			return userList.get(0);
		}
		return null;
	}

}
