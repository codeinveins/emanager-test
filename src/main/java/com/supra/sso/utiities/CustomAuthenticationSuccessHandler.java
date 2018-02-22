package com.supra.sso.utiities;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.supra.sso.model.User;
import com.supra.sso.model.UserToken;
import com.supra.sso.service.impl.SecurityServiceImpl;

//@Component(value="customSuccessHandler")
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	@Autowired
	SecurityServiceImpl securityService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth)
			throws IOException, ServletException {

		UserToken token = securityService.generateAccessToken((User)auth.getPrincipal());
		req.getSession().setAttribute("token", token.getToken());
		res.sendRedirect("welcome");
	}

}
