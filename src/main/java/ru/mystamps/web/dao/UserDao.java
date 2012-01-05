/*
 * Copyright (C) 2009-2012 Slava Semushin <slava.semushin@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package ru.mystamps.web.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ru.mystamps.web.entity.User;

@Repository
public class UserDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void add(final User user) {
		entityManager.persist(user);
	}
	
	public User findById(final Integer id) {
		return entityManager.find(User.class, id);
	}
	
	public User findByLogin(final String login) {
		try {
			final User user = (User)entityManager
				.createQuery("select u from User as u where u.login = :login")
				.setParameter("login", login)
				.getSingleResult();
			
			return user;
		} catch (final NoResultException ex) {
			return null;
		}
	}
	
}
