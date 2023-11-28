package com.test.quickshare.Configuration;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.test.quickshare.Repository.Users;
import com.test.quickshare.Repository.UsersRepo;

@Service
public class MyUser implements UserDetailsService {

	@Autowired
	UsersRepo repo;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = repo.getUser(username);
		if (user == null) {
			throw new UsernameNotFoundException(username + " does not exist. ");
		} else {
			return new MyPrincipal(user);
		}
	}

}
