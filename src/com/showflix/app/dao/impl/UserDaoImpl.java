package com.showflix.app.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.showflix.app.dao.AbstractDao;
import com.showflix.app.dao.IUserDao;
import com.showflix.app.dao.entity.User;
import com.showflix.app.dao.exceptions.DAOException;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements IUserDao {

	@Override
	public void save(User user) throws DAOException {
		try {
			persist(user);
		} catch (Exception e) {
			throw new DAOException("Database Error @ UserDaoImpl :" + e.getMessage(), e.getCause());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserByName(String name) throws DAOException {
		try{
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("firstName", name).ignoreCase());
		return (List<User>) criteria.list();
		}
		catch(Exception e){
			throw new DAOException("Database Error @ UserDaoImpl :" + e.getMessage(), e.getCause());
		}
	}

	@Override
	public void deleteUser(User user) throws DAOException {
		try{
		delete(user);
		}
		catch(Exception e){
			throw new DAOException("Database Error @ UserDaoImpl :" + e.getMessage(), e.getCause());
		}

	}

	@Override
	public User getUserById(Integer id) throws DAOException {
		try{
		return getByKey(id);
		}
		catch(Exception e){
			throw new DAOException("Database Error @ UserDaoImpl :" + e.getMessage(), e.getCause());
		}
	}

	@Override
	public User getUserByUserName(String userName) throws DAOException {
		try{
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("userName", userName).ignoreCase());

		@SuppressWarnings("unchecked")
		List<User> userList = (List<User>) criteria.list();
		if (userList.size() > 0) {
			return userList.get(0);
		}
		return null;
		}
		catch(Exception e){
			throw new DAOException("Database Error @ UserDaoImpl :" + e.getMessage(), e.getCause());
		}
	}

}
