package com.test.quickshare.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UsersRepo {

	@PersistenceContext
	EntityManager em;

	public Users getUser(String mail) {
		Users user = em.find(Users.class, mail);
		return user;
	}

	@Transactional
	public UsersData getUserData(String mail) {
		Query query = em.createQuery("select ue from UsersData ue where ue.ud.email = ?1");
		query.setParameter(1, mail);
		UsersData ud = (UsersData) query.getSingleResult();
		return ud;
	}

	@Transactional
	public int createOAuthUsers(String firstName, String lastName, String Mail) {
		int flag = 0;
		try {
			if (em.find(Users.class, Mail) != null) {
				return -2;
			}
			Users user = new Users();
			user.setEmail(Mail);
			UsersData ud = new UsersData();
			ud.setFirstName(firstName);
			ud.setLastName(lastName);
			ud.setRole("USER");
			ud.setUd(user);
			ud.setPicturePath("Google");
			em.persist(ud);
		} catch (Exception e) {
			flag = -1;
			System.out.println(e.getMessage());
		}
		return flag;
	}

	@Transactional
	public int createUsers(String firstName, String lastName, String Mail, String password) {
		int flag = 0;
		try {
			if (em.find(Users.class, Mail) != null) {
				return -2;
			}
			Users user = new Users();
			user.setEmail(Mail);
			user.setPassword(new BCryptPasswordEncoder().encode(password));
			UsersData ud = new UsersData();
			ud.setFirstName(firstName);
			ud.setLastName(lastName);
			ud.setRole("USER");
			ud.setUd(user);
			ud.setPicturePath("empty");
			em.persist(ud);
		} catch (Exception e) {
			flag = -1;
			System.out.println(e.getMessage());
		}
		return flag;
	}

	@Transactional
	public int resetPassword(String Mail, String password) {
		int flag = 0;
		try {
			Users user = em.find(Users.class, Mail);
			user.setPassword(new BCryptPasswordEncoder().encode(password));
		} catch (Exception e) {
			flag = -1;
			System.out.println(e.getMessage());
		}
		return flag;
	}
}
