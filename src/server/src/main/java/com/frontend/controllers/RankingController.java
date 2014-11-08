package com.frontend.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.backend.models.Tournament;
import com.backend.models.enums.GameTypeEnum;
import com.framework.models.Essentials;

// Show the whole tournament ranking.

@WebServlet("/ranking")
public class RankingController extends HttpServlet
{
	private static final long serialVersionUID = -6706366323781617236L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try(Essentials essentials = Essentials.createEssentials(request,  response))
		{
			Tournament tournament = Tournament.getTournament(essentials);
			
			essentials.request.setAttribute("tournament", tournament);
			essentials.request.setAttribute("ranking", tournament.getRanking(tournament.schools, GameTypeEnum.PRELIMINARY));
			essentials.request.getRequestDispatcher("/WEB-INF/frontend/ranking.jsp").forward(essentials.request, essentials.response);
		}
	}
}
