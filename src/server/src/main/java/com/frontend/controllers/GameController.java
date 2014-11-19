package com.frontend.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;

import com.backend.models.Game;
import com.framework.helpers.Helpers;
import com.framework.models.Essentials;

// Display a game with all it's events

@WebServlet("/game")
public class GameController extends HttpServlet 
{
	private static final long serialVersionUID = 357914572265696822L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try(Essentials essentials = Essentials.createEssentials(request,  response))
		{
			essentials.request.setAttribute("isLoggedIn", Boolean.FALSE);
			if(essentials.subject.isAuthenticated())
			{
				essentials.request.setAttribute("isLoggedIn", Boolean.TRUE);
			}
			
			ObjectId gameId = Helpers.getParameter("gameId", ObjectId.class, essentials);
			
			Game game = essentials.database.findOne(Game.class, gameId);
			
			essentials.request.setAttribute("game", game);
			essentials.request.getRequestDispatcher("/WEB-INF/frontend/game.jsp").forward(essentials.request, essentials.response);
		}
	}
}