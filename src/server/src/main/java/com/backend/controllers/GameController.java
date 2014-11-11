package com.backend.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;

import com.backend.models.Game;
import com.backend.models.School;
import com.backend.models.GameEvent.ActuatorStateChangedEvent;
import com.backend.models.GameEvent.GameEvent;
import com.backend.models.GameEvent.PointModifierEvent;
import com.backend.models.GameEvent.TargetHitEvent;
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
			showPage(essentials);
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
			//String 	action		= Helpers.getParameter("action", String.class, essentials);
			
			// Possible actions
			// Start game
			// Penalty CRUD
			// Point modifier CRUD
			// Target hit event
			// Actuator hit event
			// End game ?
			boolean actionProcessed = true;
			if( gameEvent.equalsIgnoreCase(GameEventEnum.START_GAME.toString()) )
			{
				game.gameEvents.add(new GameEvent(GameEventEnum.START_GAME));
			}
			else if( gameEvent.equalsIgnoreCase(GameEventEnum.ACTUATOR_CHANGED.toString()) )
			{
				SideEnum side = SideEnum.valueOf(Helpers.getParameter("side", String.class, essentials));
				TargetEnum target = TargetEnum.valueOf(Helpers.getParameter("target", String.class, essentials));
				ActuatorStateEnum actuatorState = ActuatorStateEnum.valueOf(Helpers.getParameter("actuatorState", String.class, essentials));
				
				game.gameEvents.add(new ActuatorStateChangedEvent(side, target, actuatorState));
			}
			else if( gameEvent.equalsIgnoreCase(GameEventEnum.TARGET_HIT.toString()) )
			{
				SideEnum side = SideEnum.valueOf(Helpers.getParameter("side", String.class, essentials));
				TargetEnum target = TargetEnum.valueOf(Helpers.getParameter("target", String.class, essentials));
				
				game.gameEvents.add(new TargetHitEvent(side, target));
			}
			else if( gameEvent.equalsIgnoreCase(GameEventEnum.POINT_MODIFIER.toString()) )
			{
				TeamEnum team = TeamEnum.valueOf(Helpers.getParameter("side", String.class, essentials));
				Integer points = Helpers.getParameter("target", Integer.class, essentials);
				LocalizedString comment = new LocalizedString(essentials, 
						Helpers.getParameter("commentEn", String.class, essentials),
						Helpers.getParameter("commentFr", String.class, essentials));
				
				game.gameEvents.add(new PointModifierEvent(team, points, comment));
			}
			else if( gameEvent.equalsIgnoreCase(GameEventEnum.END_GAME.toString()) )
			{
				
			}
			else
			{
				actionProcessed = false;
			}
			
			if(actionProcessed)
			{
				essentials.database.save(game);
			}
			showPage(essentials);
		}
	}
	
	private void showPage(Essentials essentials) throws ServletException, IOException
	{
		essentials.request.setAttribute("schools", School.getSchools(essentials));
		essentials.request.setAttribute("errorList", essentials.errorList);
		essentials.request.getRequestDispatcher("/WEB-INF/admin/schools.jsp").forward(essentials.request, essentials.response);
	}
}
