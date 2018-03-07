package com.supra.sso.utiities;

import java.io.IOException;
import java.util.Enumeration;

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
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {


		UserToken token = securityService.generateAccessToken((User)auth.getPrincipal());
		request.getSession().setAttribute("token", token.getToken());

		Enumeration<String> params = request.getParameterNames();
		String module = null;
		while (params.hasMoreElements()) {
			String paramName = (String) params.nextElement();
			if(paramName.equals("module"))
				module = request.getParameter(paramName); 
		}
		response.sendRedirect("welcome?module="+module);
	}
}
