package com.grabit.services;

import javax.sql.DataSource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import com.grabit.entities.Authority;
import com.grabit.entities.User;
import com.grabit.repositories.UserRepository;

@Service
public class UserService extends JdbcUserDetailsManager {

	UserRepository users;

	public UserService(UserRepository users, DataSource dataSource) {
		super(dataSource);
		this.users = users;
	}

	private User userDetailsToUser(UserDetails details) {
		var user = User.builder()
			.username(details.getUsername())
			.password(details.getPassword())
			.enabled(details.isEnabled())
			.build();

		user.getAuthorities().addAll(details.getAuthorities().stream()
			.map(a -> Authority.of(user, a.getAuthority())).toList()
		);
		return user;
	}

	@Override
	public void createUser(UserDetails details) {
		createUser(userDetailsToUser(details));
	}

	public void createUser(User user) {
		users.save(user);
	}

}
