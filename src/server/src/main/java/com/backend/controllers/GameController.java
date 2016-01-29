package com.backend.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;

import com.backend.models.Game;
import com.backend.models.GameState;
import com.backend.models.Hole;
import com.backend.models.School;
import com.backend.models.GameEvent.EndGameEvent;
import com.backend.models.GameEvent.GameEvent;
import com.backend.models.GameEvent.MisconductPenaltyEvent;
import com.backend.models.GameEvent.PointModifierEvent;
import com.backend.models.GameEvent.SchoolPenaltyEvent;
import com.backend.models.GameEvent.ScoreboardUpdateEvent;
import com.backend.models.GameEvent.StartGameEvent;
import com.backend.models.GameEvent.TeamPenaltyEvent;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.SideEnum;
import com.backend.models.enums.TeamEnum;
import com.backend.models.enums.TriangleStateEnum;
import com.framework.helpers.ApplicationSpecific;
import com.framework.helpers.Helpers;
import com.framework.helpers.LocalizedString;
import com.framework.models.Essentials;

@WebServlet("/admin/game")
public class GameController extends HttpServlet
{
	private static final long serialVersionUID = -1436275055001178541L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try(Essentials essentials = Essentials.createEssentials(request,  response))
		{
			ObjectId gameId = Helpers.getParameter("gameId", ObjectId.class, essentials);
			Game game = essentials.database.findOne(Game.class, gameId);
			showPage(essentials, game);
		}
	}
	
	private static void addToGame(Essentials essentials, Game game, GameEvent gameEvent)
	{
		boolean addSuccess = false;
		// If we don't have a insertAfter, it means we want to add it to the end
		if( essentials.request.getParameter("insertAfter") != null)
		{
			int insertAfter = Helpers.getParameter("insertAfter", Integer.class, essentials).intValue();
			addSuccess = game.addGameEvent(insertAfter, gameEvent);
		}
		else
		{
			addSuccess = game.addGameEvent(gameEvent);
		}
		
		// Something has been added to game event. Auto-refresh the page for the clients.
		if(addSuccess)
		{
			com.frontend.controllers.GameRefreshController.setRefreshNeeded(true);
		}
	}
	
	public static Game processPost(Essentials essentials)
	{
		if(!SecurityUtils.getSubject().isAuthenticated() || !SecurityUtils.getSubject().hasRole(ApplicationSpecific.AuthorizationRole.admin.toString()))
		{
			return null;
		}
		
		ObjectId gameId = null;
		Game game = null;
		String gameIdString = Helpers.getParameter("gameId", String.class, essentials);
		if( gameIdString == null )
		{
			game = Game.getLiveGame(essentials.database);
			// Currently no live game, so nothing to process.
			if( game == null )
			{
				return null;
			}
			gameId = game._id;
		}
		else
		{
			gameId = Helpers.getParameter("gameId", ObjectId.class, essentials);
			game = essentials.database.findOne(Game.class, gameId);
		}
		
		String 	gameEvent	= Helpers.getParameter("gameEvent", String.class, essentials);
		
		boolean actionProcessed = true;
		if( gameEvent.equalsIgnoreCase(GameEventEnum.START_GAME.toString()) )
		{
			game = Game.setLiveGame(essentials.database, game._id, true);
			// Reset any state the game could had previously.
			game = game.getInitialState();
			
			addToGame(essentials, game, new StartGameEvent(DateTime.now()));
			
			Game.createEndGameCallback(gameId);
		}
		else if( gameEvent.equalsIgnoreCase(GameEventEnum.SCOREBOARD_UPDATED.toString()) )
		{
			Hole[] triangleSide1 = getTriangleFromParameters(SideEnum.SIDE1, essentials);
			Hole[] triangleSide2 = getTriangleFromParameters(SideEnum.SIDE2, essentials);
			
			addToGame(essentials, game, new ScoreboardUpdateEvent(triangleSide1, triangleSide2, DateTime.now()));
		}
		else if( gameEvent.equalsIgnoreCase(GameEventEnum.SCHOOL_PENALTY.toString()) )
		{
			ObjectId schoolId = Helpers.getParameter("school", ObjectId.class, essentials);
			Integer points = Helpers.getParameter("points", Integer.class, essentials);
			School school = essentials.database.findOne(School.class, schoolId);
			
			addToGame(essentials, game, new SchoolPenaltyEvent(school, points, DateTime.now()));
		}
		else if( gameEvent.equalsIgnoreCase(GameEventEnum.TEAM_PENALTY.toString()) )
		{
			TeamEnum team = TeamEnum.valueOf(Helpers.getParameter("team", String.class, essentials));
			Integer points = Helpers.getParameter("points", Integer.class, essentials);
							
			addToGame(essentials, game, new TeamPenaltyEvent(team, points, DateTime.now()));
		}
		else if( gameEvent.equalsIgnoreCase(GameEventEnum.MISCONDUCT_PENALTY.toString()) )
		{
			ObjectId schoolId = Helpers.getParameter("school", ObjectId.class, essentials);
			School school = essentials.database.findOne(School.class, schoolId);
			
			addToGame(essentials, game, new MisconductPenaltyEvent(school, DateTime.now()));
		}
		else if( gameEvent.equalsIgnoreCase(GameEventEnum.POINT_MODIFIER.toString()) )
		{
			TeamEnum team = TeamEnum.valueOf(Helpers.getParameter("team", String.class, essentials));
			Integer points = Helpers.getParameter("points", Integer.class, essentials);
			LocalizedString comment = new LocalizedString(essentials, 
					Helpers.getParameter("commentEn", String.class, essentials),
					Helpers.getParameter("commentFr", String.class, essentials));
			
			addToGame(essentials, game, new PointModifierEvent(team, points, comment, DateTime.now()));
		}
		else if( gameEvent.equalsIgnoreCase(GameEventEnum.END_GAME.toString()) )
		{
			addToGame(essentials, game, new EndGameEvent(DateTime.now()));
		}
		else if( gameEvent.equalsIgnoreCase("delete") )
		{
			Integer gameEventIndex = Helpers.getParameter("gameEventIndex", Integer.class, essentials);
			game.removeGameEvent(gameEventIndex.intValue());
		}
		else
		{
			actionProcessed = false;
		}
		
		if(actionProcessed)
		{
			essentials.database.save(game);
		}
		
		return game;
	}
	
	private static Hole[] getTriangleFromParameters(SideEnum side, Essentials essentials)
	{
		Hole[] triangle = GameState.InitializeTriangle();
		
		for(int holeNb = 0; holeNb < triangle.length; holeNb++)
		{
			for(int posInHole = 0; posInHole < triangle[holeNb].triangleStates.length; posInHole++)
			{
				String parameterName = "hole_" + side.toString() + "_" + holeNb + "_" + posInHole;
				TriangleStateEnum triangleState = TriangleStateEnum.valueOf(Helpers.getParameter(parameterName, String.class, essentials));
				triangle[holeNb].triangleStates[posInHole] = triangleState;
			}
		}
		
		return triangle;
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try(Essentials essentials = Essentials.createEssentials(request,  response))
		{
			Game game = processPost(essentials);
			showPage(essentials, game);
		}
	}
	
	private void showPage(Essentials essentials, Game game) throws ServletException, IOException
	{
		essentials.request.setAttribute("game", game);
		essentials.request.setAttribute("errorList", essentials.errorList);
		
		essentials.request.getRequestDispatcher("/WEB-INF/admin/game.jsp").forward(essentials.request, essentials.response);
	}
}
