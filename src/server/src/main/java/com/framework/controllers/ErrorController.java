package com.framework.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.framework.helpers.LocalizedString;


@WebServlet("/error")
public class ErrorController extends HttpServlet
{
	private static final long serialVersionUID = 4404619373174480466L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setAttribute("errorList", request.getAttribute("errorList") != null ? request.getAttribute("errorList") : new ArrayList<LocalizedString>() );
		request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setAttribute("errorList", request.getAttribute("errorList") != null ? request.getAttribute("errorList") : new ArrayList<LocalizedString>() );
		request.getRequestDispatcher("/WEB-INF/framework/error.jsp").forward(request, response);
	}
}