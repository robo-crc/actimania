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
import com.framework.helpers.Helpers;
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
			
			String 	action 		= Helpers.getParameter("action", String.class, essentials);
			
			// Possible actions
			// Start game
			// Penalty CRUD
			// Point modifier CRUD
			// Target hit event
			// Actuator hit event
			// End game ?
			/*
			ObjectId id = null;
			if(action.equals("edit") || action.equals("delete"))
			{
				id 	= Helpers.getParameter("id", ObjectId.class, essentials);
			}
			
			if(action.equals("create") || action.equals("edit"))
			{
				essentials.database.save(school);
			}
			else if(action.equals("delete"))
			{
				essentials.database.remove(School.class, id);
			}
			*/
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
