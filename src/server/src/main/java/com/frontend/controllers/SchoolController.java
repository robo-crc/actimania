package com.frontend.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;

import com.backend.models.School;
import com.backend.models.SkillsCompetition;
import com.backend.models.Tournament;
import com.backend.models.enums.GameTypeEnum;
import com.framework.helpers.Helpers;
import com.framework.models.Essentials;

// Display the list of games played by the school
// Can also show the scores in other area

@WebServlet("/school")
public class SchoolController extends HttpServlet
{
	private static final long serialVersionUID = 1842100102197583562L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try(Essentials essentials = Essentials.createEssentials(request,  response))
		{
			School school = essentials.database.findOne(School.class, Helpers.getParameter("schoolId", ObjectId.class, essentials));
			
			Tournament tournament = Tournament.getTournament(essentials);
			
			essentials.request.setAttribute("tournament", tournament);
			essentials.request.setAttribute("school", school);
			essentials.request.setAttribute("rank", tournament.getRoundRanking(GameTypeEnum.PRELIMINARY).indexOf(school) + 1);
			essentials.request.setAttribute("score", tournament.getRoundScore(school, GameTypeEnum.PRELIMINARY));
			essentials.request.setAttribute("skillsCompetition", SkillsCompetition.get(essentials.database));
			essentials.request.getRequestDispatcher("/WEB-INF/frontend/school.jsp").forward(essentials.request, essentials.response);
		}
	}
}
