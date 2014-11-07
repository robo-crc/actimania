package com.frontend.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.backend.models.Tournament;
import com.framework.models.Essentials;

// Display the list of games played by the school
// Can also show the scores in other area

@WebServlet("/schedule")
public class ScheduleController extends HttpServlet
{
	private static final long serialVersionUID = 5614373930998586935L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try(Essentials essentials = Essentials.createEssentials(request,  response))
		{
			Tournament tournament = Tournament.getTournament(essentials);

			essentials.request.setAttribute("tournament", tournament);
			essentials.request.getRequestDispatcher("/WEB-INF/frontend/schedule.jsp").forward(essentials.request, essentials.response);
		}
	}
}
