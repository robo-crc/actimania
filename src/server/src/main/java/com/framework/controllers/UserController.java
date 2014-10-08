package com.framework.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;

import com.framework.helpers.ApplicationSpecific;
import com.framework.helpers.Helpers;
import com.framework.models.Essentials;
import com.framework.models.User;
import com.google.common.collect.ImmutableSet;

@WebServlet("/admin/users")
public class UserController extends HttpServlet
{
	private static final long serialVersionUID = 6202503836759704489L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try(Essentials essentials = Essentials.createEssentials(request,  response))
		{
			showPage(essentials);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try(Essentials essentials = Essentials.createEssentials(request,  response))
		{
			String email 	= Helpers.getParameter("email", String.class, essentials);
			String password = Helpers.getParameter("password", String.class, essentials);
			String role 	= Helpers.getParameter("role", String.class, essentials);
			
			String 	action 				= Helpers.getParameter("action", String.class, essentials);
			if(action.equals("create"))
			{
				User user = User.createUser(essentials, ImmutableSet.of(role), email, password);
				ApplicationSpecific.onAccountCreated(essentials, user);
			}
			else if(action.equals("edit"))
			{
				ObjectId id 	= Helpers.getParameter("id", ObjectId.class, essentials);
				User.editUser(essentials, id, ImmutableSet.of(role), email, password);
			}
			else if(action.equals("delete"))
			{
				ObjectId id 	= Helpers.getParameter("id", ObjectId.class, essentials);
				User.deleteUser(essentials, id);
			}
			showPage(essentials);
		}
	}
	
	private void showPage(Essentials essentials) throws ServletException, IOException
	{
		essentials.request.setAttribute("users", User.getAllUsers(essentials));
		essentials.request.setAttribute("roles", ApplicationSpecific.getAllRoles());
		essentials.request.setAttribute("defaultRole", ApplicationSpecific.getDefaultRole());
		essentials.request.getRequestDispatcher("/WEB-INF/framework/users.jsp").forward(essentials.request, essentials.response);
	}
}
