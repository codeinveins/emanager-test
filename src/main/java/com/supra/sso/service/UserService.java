package com.supra.sso.service;

import com.supra.sso.model.User;

public interface UserService {

	void save(User user);
	User findByUsername(String username);
}
