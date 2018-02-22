package com.supra.sso.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.supra.sso.model.UserToken;

public interface SecurityService {

	void autologin(String username, String password);
	UserToken generateAccessToken(UserDetails user);
	UserDetails findLoggedInUser();
}
