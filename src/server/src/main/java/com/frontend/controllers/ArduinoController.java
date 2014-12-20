package com.frontend.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;

import com.backend.controllers.GameController;
import com.framework.controllers.LoginController;
import com.framework.helpers.Helpers;
import com.framework.models.Essentials;
import com.framework.models.User;

@WebServlet("/arduino")
public class ArduinoController extends HttpServlet
{
	private static final long serialVersionUID = -3927544002409245775L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try(Essentials essentials = Essentials.createEssentials(request,  response))
		{
			User user = null;
			if(!SecurityUtils.getSubject().isAuthenticated())
			{
				user = LoginController.login(essentials);
			}
			else
			{
				user = Helpers.getCurrentUser();
			}
			
			if(user != null)
			{
				GameController.processPost(essentials);
			}
		}
	}
}
