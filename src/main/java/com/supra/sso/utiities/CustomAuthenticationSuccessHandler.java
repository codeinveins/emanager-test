package com.supra.sso.utiities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
		performAutoLogin(auth.getName(), auth.getCredentials().toString(), ApplicationConstants.ATTENDANCE_MODULE, "8082");
		performAutoLogin(auth.getName(), auth.getCredentials().toString(), ApplicationConstants.TIMESHEET_MODULE, "8081");
		response.sendRedirect("welcome?module="+module);
	}


	public void performAutoLogin(String username, String password, String moduleName, String portNumber) {
		try {
			URL url = new URL("http://localhost:"+portNumber+"/"+moduleName+"/autologin"+moduleName+"?username="+username+"&password="+password);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			//conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}
