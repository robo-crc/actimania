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
			showPage(essentials);
			
			String 	action 		= Helpers.getParameter("action", String.class, essentials);
			Tournament tournament = Tournament.getTournament(essentials);
			
			Playoff playoff = essentials.database.findOne(Playoff.class, "{ }");
			
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
				
				PlayoffRound playoffRound = playoff.generatePlayoffRound(tournament, currentRound, nextGameType);
				
				DateTime startTime = null;
				switch(nextGameType)
				{
				case PLAYOFF_DRAFT:
					startTime = new DateTime(2014, 2, 14, 9, 0);
					break;
				case PLAYOFF_SEMI:
					startTime = new DateTime(2014, 2, 14, 12, 30);
					break;
				case PLAYOFF_DEMI:
					startTime = new DateTime(2014, 2, 14, 14, 50);
					break;
				case PLAYOFF_FINAL:
					startTime = new DateTime(2014, 2, 14, 16, 40);
					break;
				default:
					break;
				}
				
				ArrayList<Game> playoffGames = playoffRound.getGames(startTime, tournament.games.size() + 1);
				tournament.games.addAll(playoffGames);
				
				essentials.database.save(playoffRound);
				essentials.database.save(tournament);
			}
			else if(action.equals("addExcludedSchool"))
			{
				ObjectId schoolId = Helpers.getParameter("schoolId", ObjectId.class, essentials);

				School schoolToAdd = essentials.database.findOne(School.class, schoolId);
				playoff.excludedSchools.add(schoolToAdd);
				
				essentials.database.save(playoff);
			}
			else if(action.equals("removeExcludedSchool"))
			{
				ObjectId schoolId = Helpers.getParameter("schoolId", ObjectId.class, essentials);
				
				School schoolToRemove = essentials.database.findOne(School.class, schoolId);
				playoff.excludedSchools.remove(schoolToRemove);
				
				essentials.database.save(playoff);
			}
		}
	}
	
	private void showPage(Essentials essentials) throws ServletException, IOException
	{
		Tournament tournament = Tournament.getTournament(essentials);
		
		GameTypeEnum currentRound = null;
		GameTypeEnum nextRound = null;
		Boolean isCurrentRoundStarted = false;
		
		if(tournament.getHeatRanking(GameTypeEnum.PLAYOFF_DRAFT).size() == 0)
		{
			currentRound = null;
			nextRound = GameTypeEnum.PLAYOFF_DRAFT;
		}
		else if(tournament.getHeatRanking(GameTypeEnum.PLAYOFF_SEMI).size() == 0)
		{
			currentRound = GameTypeEnum.PLAYOFF_DRAFT;
			isCurrentRoundStarted = tournament.getHeatGames(GameTypeEnum.PLAYOFF_DRAFT).get(0).getGameStates().size() == 0;
			nextRound = GameTypeEnum.PLAYOFF_SEMI;
		}
		else if(tournament.getHeatRanking(GameTypeEnum.PLAYOFF_DEMI).size() == 0)
		{
			currentRound = GameTypeEnum.PLAYOFF_SEMI;
			isCurrentRoundStarted = tournament.getHeatGames(GameTypeEnum.PLAYOFF_SEMI).get(0).getGameStates().size() == 0;
			nextRound = GameTypeEnum.PLAYOFF_DEMI;
		}
		else if(tournament.getHeatRanking(GameTypeEnum.PLAYOFF_FINAL).size() == 0)
		{
			currentRound = GameTypeEnum.PLAYOFF_DEMI;
			isCurrentRoundStarted = tournament.getHeatGames(GameTypeEnum.PLAYOFF_DEMI).get(0).getGameStates().size() == 0;
			nextRound = GameTypeEnum.PLAYOFF_FINAL;
		}
		else
		{
			currentRound = GameTypeEnum.PLAYOFF_FINAL;
			isCurrentRoundStarted = tournament.getHeatGames(GameTypeEnum.PLAYOFF_FINAL).get(0).getGameStates().size() == 0;
			nextRound = null;
		}
		
		essentials.request.setAttribute("currentRound", currentRound);
		essentials.request.setAttribute("isCurrentRoundStarted", isCurrentRoundStarted);
		essentials.request.setAttribute("nextRound", nextRound);
		essentials.request.setAttribute("tournament", tournament);
	}
}
