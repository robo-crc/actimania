package com.backend.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;

import com.backend.models.Game;
import com.backend.models.School;
import com.backend.models.GameEvent.ActuatorStateChangedEvent;
import com.backend.models.GameEvent.EndGameEvent;
import com.backend.models.GameEvent.MisconductPenaltyEvent;
import com.backend.models.GameEvent.PointModifierEvent;
import com.backend.models.GameEvent.SchoolPenaltyEvent;
import com.backend.models.GameEvent.StartGameEvent;
import com.backend.models.GameEvent.TargetHitEvent;
import com.backend.models.GameEvent.TeamPenaltyEvent;
import com.backend.models.enums.ActuatorStateEnum;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.SideEnum;
import com.backend.models.enums.TargetEnum;
import com.backend.models.enums.TeamEnum;
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
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try(Essentials essentials = Essentials.createEssentials(request,  response))
		{
			ObjectId gameId = Helpers.getParameter("gameId", ObjectId.class, essentials);
			Game game = essentials.database.findOne(Game.class, gameId);
			
			String 	gameEvent	= Helpers.getParameter("gameEvent", String.class, essentials);
			
			// Possible actions
			// Start game
			// Penalty CRUD
			// Point modifier CRUD
			// Target hit event
			// Actuator hit event
			// End game
			boolean actionProcessed = true;
			if( gameEvent.equalsIgnoreCase(GameEventEnum.START_GAME.toString()) )
			{
				game = Game.setLiveGame(essentials, game._id);
				// Reset any state the game could had previously.
				game = game.getGameInitialState();
				
				game.addGameEvent(new StartGameEvent(DateTime.now()));
				
				Game.processEndGame(gameId);
			}
			else if( gameEvent.equalsIgnoreCase(GameEventEnum.ACTUATOR_STATE_CHANGED.toString()) )
			{
				int insertAfter 	= Helpers.getParameter("insertAfter", Integer.class, essentials).intValue();
				SideEnum side = SideEnum.valueOf(Helpers.getParameter("side", String.class, essentials));
				TargetEnum target = TargetEnum.valueOf(Helpers.getParameter("target", String.class, essentials));
				ActuatorStateEnum actuatorState = ActuatorStateEnum.valueOf(Helpers.getParameter("actuatorState", String.class, essentials));
				
				game.addGameEvent(insertAfter, new ActuatorStateChangedEvent(side, target, actuatorState, DateTime.now()));
			}
			else if( gameEvent.equalsIgnoreCase(GameEventEnum.TARGET_HIT.toString()) )
			{
				int insertAfter 	= Helpers.getParameter("insertAfter", Integer.class, essentials).intValue();
				SideEnum side = SideEnum.valueOf(Helpers.getParameter("side", String.class, essentials));
				TargetEnum target = TargetEnum.valueOf(Helpers.getParameter("target", String.class, essentials));
				
				game.addGameEvent(insertAfter, new TargetHitEvent(side, target, DateTime.now()));
			}
			else if( gameEvent.equalsIgnoreCase(GameEventEnum.SCHOOL_PENALTY.toString()) )
			{
				int insertAfter 	= Helpers.getParameter("insertAfter", Integer.class, essentials).intValue();
				ObjectId schoolId = Helpers.getParameter("school", ObjectId.class, essentials);
				Integer points = Helpers.getParameter("points", Integer.class, essentials);
				School school = essentials.database.findOne(School.class, schoolId);
				
				game.addGameEvent(insertAfter, new SchoolPenaltyEvent(school, points, DateTime.now()));
			}
			else if( gameEvent.equalsIgnoreCase(GameEventEnum.TEAM_PENALTY.toString()) )
			{
				int insertAfter 	= Helpers.getParameter("insertAfter", Integer.class, essentials).intValue();
				TeamEnum team = TeamEnum.valueOf(Helpers.getParameter("team", String.class, essentials));
				Integer points = Helpers.getParameter("points", Integer.class, essentials);
								
				game.addGameEvent(insertAfter, new TeamPenaltyEvent(team, points, DateTime.now()));
			}
			else if( gameEvent.equalsIgnoreCase(GameEventEnum.MISCONDUCT_PENALTY.toString()) )
			{
				int insertAfter 	= Helpers.getParameter("insertAfter", Integer.class, essentials).intValue();
				ObjectId schoolId = Helpers.getParameter("school", ObjectId.class, essentials);
				School school = essentials.database.findOne(School.class, schoolId);
				
				game.addGameEvent(insertAfter, new MisconductPenaltyEvent(school, DateTime.now()));
			}
			else if( gameEvent.equalsIgnoreCase(GameEventEnum.POINT_MODIFIER.toString()) )
			{
				int insertAfter 	= Helpers.getParameter("insertAfter", Integer.class, essentials).intValue();
				TeamEnum team = TeamEnum.valueOf(Helpers.getParameter("side", String.class, essentials));
				Integer points = Helpers.getParameter("points", Integer.class, essentials);
				LocalizedString comment = new LocalizedString(essentials, 
						Helpers.getParameter("commentEn", String.class, essentials),
						Helpers.getParameter("commentFr", String.class, essentials));
				
				game.addGameEvent(insertAfter, new PointModifierEvent(team, points, comment, DateTime.now()));
			}
			else if( gameEvent.equalsIgnoreCase(GameEventEnum.END_GAME.toString()) )
			{
				game.addGameEvent(new EndGameEvent(DateTime.now()));
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
