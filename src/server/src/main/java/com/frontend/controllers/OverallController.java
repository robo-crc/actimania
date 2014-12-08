package com.frontend.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.backend.models.Competition;
import com.backend.models.Tournament;
import com.framework.models.Essentials;

@WebServlet("/overall")
public class OverallController extends HttpServlet
{
	private static final long serialVersionUID = -2575101334482168155L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try(Essentials essentials = Essentials.createEssentials(request,  response))
		{
			Tournament tournament = Tournament.getTournament(essentials);
			Competition competition = Competition.get(essentials);
			essentials.request.setAttribute("tournament", tournament);
			essentials.request.setAttribute("competition", competition);
			essentials.request.setAttribute("schoolsRanked", competition.getCompetitionRanking(essentials));

			essentials.request.getRequestDispatcher("/WEB-INF/frontend/overall.jsp").forward(essentials.request, essentials.response);
		}
	}
}
