package com.backend.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.backend.models.Tournament;
import com.backend.models.enums.GameTypeEnum;
import com.framework.helpers.Helpers;
import com.framework.models.Essentials;

@WebServlet("/admin/playoff")
public class PlayoffController extends HttpServlet
{
	private static final long serialVersionUID = 2153175791245241965L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try(Essentials essentials = Essentials.createEssentials(request,  response))
		{
			showPage(essentials);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try(Essentials essentials = Essentials.createEssentials(request,  response))
		{
			showPage(essentials);
			
			String 	action 		= Helpers.getParameter("action", String.class, essentials);
			
			if(action.equals("deleteCurrentRound"))
			{
			}
			else if(action.equals("generateNextRound"))
			{
				
			}
		}
	}
	
	private void showPage(Essentials essentials) throws ServletException, IOException
	{
		Tournament tournament = Tournament.getTournament(essentials);
		
		GameTypeEnum currentRoundType = null;
		GameTypeEnum nextRoundType = null;
		Boolean isCurrentRoundStarted = false;
		
		if(tournament.getHeatRanking(GameTypeEnum.PLAYOFF_DRAFT).size() == 0)
		{
			currentRoundType = null;
			nextRoundType = GameTypeEnum.PLAYOFF_DRAFT;
		}
		else if(tournament.getHeatRanking(GameTypeEnum.PLAYOFF_SEMI).size() == 0)
		{
			currentRoundType = GameTypeEnum.PLAYOFF_DRAFT;
			isCurrentRoundStarted = tournament.getHeatGames(GameTypeEnum.PLAYOFF_DRAFT).get(0).getGameStates().size() == 0;
			nextRoundType = GameTypeEnum.PLAYOFF_SEMI;
		}
		else if(tournament.getHeatRanking(GameTypeEnum.PLAYOFF_DEMI).size() == 0)
		{
			currentRoundType = GameTypeEnum.PLAYOFF_SEMI;
			isCurrentRoundStarted = tournament.getHeatGames(GameTypeEnum.PLAYOFF_SEMI).get(0).getGameStates().size() == 0;
			nextRoundType = GameTypeEnum.PLAYOFF_DEMI;
		}
		else if(tournament.getHeatRanking(GameTypeEnum.PLAYOFF_FINAL).size() == 0)
		{
			currentRoundType = GameTypeEnum.PLAYOFF_DEMI;
			isCurrentRoundStarted = tournament.getHeatGames(GameTypeEnum.PLAYOFF_DEMI).get(0).getGameStates().size() == 0;
			nextRoundType = GameTypeEnum.PLAYOFF_FINAL;
		}
		else
		{
			currentRoundType = GameTypeEnum.PLAYOFF_FINAL;
			isCurrentRoundStarted = tournament.getHeatGames(GameTypeEnum.PLAYOFF_FINAL).get(0).getGameStates().size() == 0;
			nextRoundType = null;
		}
		
		essentials.request.setAttribute("currentRoundType", currentRoundType);
		essentials.request.setAttribute("isCurrentRoundStarted", isCurrentRoundStarted);
		essentials.request.setAttribute("nextRoundType", nextRoundType);
		essentials.request.setAttribute("tournament", tournament);
	}
}
