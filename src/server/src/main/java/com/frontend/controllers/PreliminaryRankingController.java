package com.frontend.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.backend.models.Playoff;
import com.backend.models.School;
import com.backend.models.SkillsCompetition;
import com.backend.models.Tournament;
import com.backend.models.enums.GameTypeEnum;
import com.framework.models.Essentials;

// Show the preliminary tournament ranking.

@WebServlet("/ranking")
public class PreliminaryRankingController extends HttpServlet
{
	private static final long serialVersionUID = -6706366323781617236L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try(Essentials essentials = Essentials.createEssentials(request,  response))
		{
			Tournament tournament = Tournament.getTournament(essentials);
			
			SkillsCompetition skillsCompetition = SkillsCompetition.get(essentials.database);
			
			essentials.request.setAttribute("tournament", tournament);
			essentials.request.setAttribute("heatRanking", tournament.getRoundRanking(GameTypeEnum.PRELIMINARY));
			essentials.request.setAttribute("cumulativeRanking", tournament.getPreliminaryRanking(skillsCompetition));
			essentials.request.setAttribute("skillsCompetition", skillsCompetition);
			Playoff playoff = Playoff.get(essentials.database);
			if(playoff != null)
			{
				essentials.request.setAttribute("excludedSchools", Playoff.get(essentials.database).excludedSchools);
			}
			else
			{
				essentials.request.setAttribute("excludedSchools", new ArrayList<School>());
			}
			
			essentials.request.getRequestDispatcher("/WEB-INF/frontend/ranking.jsp").forward(essentials.request, essentials.response);
		}
	}
}
