package com.backend.controllers;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.framework.models.Essentials;

@WebServlet("/admin/competitionUpdate")
public class CompetitionUpdate extends HttpServlet
{
	private static final long serialVersionUID = -1616482657278664972L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try(Essentials essentials = Essentials.createEssentials(request,  response))
		{
			for (Enumeration<String> iter = request.getParameterNames(); iter.hasMoreElements(); ) 
			{
			    System.out.println(iter.nextElement());
			}
			
			for (String string : request.getParameterValues("arrayId")) 
			{
			    System.out.println(string);
			}
			
			for (String string : request.getParameterValues("id[]")) 
			{
			    System.out.println(string);
			}
		}
	}
}
