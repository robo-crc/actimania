package com.framework.controllers;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;

import com.framework.helpers.ApplicationSpecific;
import com.framework.helpers.Helpers;
import com.framework.helpers.LocalizedString;
import com.framework.models.Essentials;
import com.framework.models.User;
import com.google.common.collect.ImmutableMap;


@WebServlet("/login")
public class LoginController extends HttpServlet
{
	private static final long serialVersionUID = -770351192685998326L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try(Essentials essentials = Essentials.createEssentials(request,  response))
		{
			request.getRequestDispatcher("/WEB-INF/framework/login.jsp").forward(request, response);
		}
	}
	
	public static User login(Essentials essentials) throws IOException, ServletException
	{
		User user = null;
		String email 	= Helpers.getParameter("email", String.class, essentials);
		String password = Helpers.getParameter("password", String.class, essentials);
		
		try
		{
			UsernamePasswordToken token = new UsernamePasswordToken(email, password);
			token.setRememberMe(true);
			
			essentials.subject.login(token);
			
			// If we are here, the login was successful
			// Easy access to useful info
			user = User.getUser(essentials, email);
			essentials.subject.getSession().setAttribute("user", user);
		}
	
		catch(AuthenticationException e)
		{
			LocalizedString strError = new LocalizedString(ImmutableMap.of( 	
					Locale.ENGLISH, "Your email or password is incorrect, please try again.", 
					Locale.FRENCH, 	"Votre courriel ou mot de passe sont incorrect. SVP Essayez à nouveau."
					), essentials.request.getLocale());
			
			essentials.errorList.add(strError);
			
			essentials.request.getRequestDispatcher("/WEB-INF/framework/login.jsp").forward(essentials.request, essentials.response);
		}
		return user;
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try(Essentials essentials = Essentials.createEssentials(request,  response))
		{
			User user = login(essentials);
			if(user != null)
			{
				ApplicationSpecific.onLoggedIn(essentials, user);
			}
		}
	}
}
