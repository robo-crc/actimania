package com.backend.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;

import com.backend.controllers.yearly.PlayoffYearlyController;
import com.backend.models.Game;
import com.backend.models.Playoff;
import com.backend.models.PlayoffRound;
import com.backend.models.School;
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
			String 	action 		= Helpers.getParameter("action", String.class, essentials);
			Tournament tournament = Tournament.getTournament(essentials);
			
			Playoff playoff = Playoff.get(essentials.database);
			
			if(action.equals("deleteCurrentRound"))
			{
				GameTypeEnum gameType = Helpers.getParameter("currentRound", GameTypeEnum.class, essentials);
				PlayoffRound playoffRound = PlayoffRound.get(essentials.database, gameType);
				essentials.database.remove(PlayoffRound.class, playoffRound._id);
				
				for(Game game : tournament.getHeatGames(gameType))
				{
					essentials.database.remove(Game.class, game._id);
				}
			}
			else if(action.equals("generateNextRound"))
			{
				GameTypeEnum nextGameType = Helpers.getParameter("nextRound", GameTypeEnum.class, essentials);
				GameTypeEnum currentGameType = Helpers.getParameter("currentRound", GameTypeEnum.class, essentials);
				PlayoffRound currentRound = PlayoffRound.get(essentials.database, currentGameType);
				
				if(playoff == null)
				{
		            playoff = new Playoff(null, new ArrayList<School>(), null);
				}
				
				PlayoffRound playoffRound = playoff.generatePlayoffRound(essentials.database, tournament, currentRound, nextGameType);
				
				DateTime startTime = PlayoffYearlyController.GetPlayoffRoundStartTime(nextGameType);
				
				ArrayList<Game> playoffGames = playoffRound.getGames(startTime, tournament.games.size() + 1);
				essentials.database.save(playoffRound);
				
				for(Game game : playoffGames)
				{
					essentials.database.save(game);
				}
			}
			else if(action.equals("addExcludedSchool"))
			{
				ObjectId schoolId = Helpers.getParameter("school", ObjectId.class, essentials);

				School schoolToAdd = essentials.database.findOne(School.class, schoolId);
				if(playoff == null)
				{
		            playoff = new Playoff(null, new ArrayList<School>(), null);
				}
				playoff.excludedSchools.add(schoolToAdd);
				
				essentials.database.save(playoff);
			}
			else if(action.equals("removeExcludedSchool"))
			{
				ObjectId schoolId = Helpers.getParameter("school", ObjectId.class, essentials);
				
				School schoolToRemove = essentials.database.findOne(School.class, schoolId);
				playoff.excludedSchools.remove(schoolToRemove);
				
				essentials.database.save(playoff);
			}
			showPage(essentials);
		}
	}
	
	private void showPage(Essentials essentials) throws ServletException, IOException
	{
		Tournament tournament = Tournament.getTournament(essentials);
		
		GameTypeEnum currentRound = GameTypeEnum.NONE;
		GameTypeEnum nextRound = GameTypeEnum.NONE;
		Boolean isCurrentRoundStarted = false;
		
		if(tournament.getRoundRanking(GameTypeEnum.PLAYOFF_REPECHAGE).size() == 0)
		{
			currentRound = GameTypeEnum.NONE;
			nextRound = GameTypeEnum.PLAYOFF_REPECHAGE;
		}
		else if(tournament.getRoundRanking(GameTypeEnum.PLAYOFF_QUARTER).size() == 0)
		{
			currentRound = GameTypeEnum.PLAYOFF_REPECHAGE;
			isCurrentRoundStarted = tournament.getHeatGames(GameTypeEnum.PLAYOFF_REPECHAGE).get(0).getGameStates().size() == 0;
			nextRound = GameTypeEnum.PLAYOFF_QUARTER;
		}
		else if(tournament.getRoundRanking(GameTypeEnum.PLAYOFF_DEMI).size() == 0)
		{
			currentRound = GameTypeEnum.PLAYOFF_QUARTER;
			isCurrentRoundStarted = tournament.getHeatGames(GameTypeEnum.PLAYOFF_QUARTER).get(0).getGameStates().size() > 0;
			nextRound = GameTypeEnum.PLAYOFF_DEMI;
		}
		else if(tournament.getRoundRanking(GameTypeEnum.PLAYOFF_FINAL).size() == 0)
		{
			currentRound = GameTypeEnum.PLAYOFF_DEMI;
			isCurrentRoundStarted = tournament.getHeatGames(GameTypeEnum.PLAYOFF_DEMI).get(0).getGameStates().size() > 0;
			nextRound = GameTypeEnum.PLAYOFF_FINAL;
		}
		else
		{
			currentRound = GameTypeEnum.PLAYOFF_FINAL;
			isCurrentRoundStarted = tournament.getHeatGames(GameTypeEnum.PLAYOFF_FINAL).get(0).getGameStates().size() > 0;
			nextRound = GameTypeEnum.NONE;
		}
		
		Playoff playoff = Playoff.get(essentials.database);
		ArrayList<School> excludedSchools = new ArrayList<School>();
		if(playoff != null)
		{
			excludedSchools = playoff.excludedSchools;
		}
		essentials.request.setAttribute("excludedSchools", excludedSchools);
		essentials.request.setAttribute("currentRound", currentRound );
		essentials.request.setAttribute("isCurrentRoundStarted", isCurrentRoundStarted);
		essentials.request.setAttribute("nextRound", nextRound );
		essentials.request.setAttribute("tournament", tournament);
		essentials.request.setAttribute("playoff", playoff);
		
		essentials.request.getRequestDispatcher("/WEB-INF/admin/playoff.jsp").forward(essentials.request, essentials.response);
	}
}
