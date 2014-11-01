package com.frontend.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.backend.models.Game;
import com.backend.models.Tournament;
import com.framework.models.Essentials;

@WebServlet("/game/live")
public class LiveController extends HttpServlet
{
	private static final long serialVersionUID = -6165679298971913201L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try(Essentials essentials = Essentials.createEssentials(request,  response))
		{
			Game game = Game.getLiveGame(essentials);
			if(game == null)
			{
				Tournament tournament = Tournament.getTournament(essentials);
				// There's no live game actually, find next game.
				game = Tournament.getNextGame(tournament.games);
			}
			
			if(game != null)
			{
				// We should have live refresh and stuff.
				essentials.request.setAttribute("game", game);
				essentials.request.getRequestDispatcher("/WEB-INF/frontend/live.jsp").forward(essentials.request, essentials.response);
			}
			else
			{
				// Display that there's no live content for now.
			}
		}
	}

}
