package com.hjp.shiro.dao;

import java.util.List;

import org.apache.shiro.util.Assert;

import com.hjp.shiro.model.User;

public class HibernateUserDAO extends HibernateDao implements UserDAO {

	public User getUser(Long userId) {

		return (User) getSession().get(User.class, userId);
	}

	public User findUser(String username) {
		Assert.hasText(username);
		String querry = "from User u where u.username=:username";
		return (User) getSession().createSQLQuery(querry).setString("username", username).uniqueResult();
	}

	public void createUser(User user) {
		getSession().save(user);
	}

	public List<User> getAllUsers() {
		return getSession().createQuery("from User order by username").list();
	}

	public void deleteUser(Long userId) {
		User user = getUser(userId);
		if (user != null) {
			getSession().update(user);
		}
	}

	public void updateUser(User user) {
		getSession().update(user);
	}
}
