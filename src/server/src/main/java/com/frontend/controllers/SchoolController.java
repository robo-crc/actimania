package com.frontend.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;

import com.backend.models.Game;
import com.backend.models.School;
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
			ObjectId schoolId = Helpers.getParameter("schoolId", ObjectId.class, essentials);
			
			Tournament tournament = Tournament.getTournament(essentials);
			
			tournament.getGamesPlayed(tournament.games, new School(id, ""), GameTypeEnum.PRELIMINARY);
			
			essentials.request.setAttribute("tournament", tournament);
			essentials.request.setAttribute("errorList", essentials.errorList);
			essentials.request.getRequestDispatcher("/WEB-INF/game.jsp").forward(essentials.request, essentials.response);
		}
	}
}
