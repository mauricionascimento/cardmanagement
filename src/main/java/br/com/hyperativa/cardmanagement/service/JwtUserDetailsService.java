package br.com.hyperativa.cardmanagement.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		 br.com.hyperativa.cardmanagement.model.User user = userService.getByLogin(login);

		if (user.getLogin().equals(login)) {
			return new User(login, user.getSenha(), new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with login: " + login);
		}
	}
}
