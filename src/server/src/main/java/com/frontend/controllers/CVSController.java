package com.frontend.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;

import com.backend.models.Game;
import com.backend.models.Tournament;
import com.framework.helpers.Helpers;
import com.framework.models.Essentials;

@WebServlet("/cvs")
public class CVSController extends HttpServlet 
{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try(Essentials essentials = Essentials.createEssentials(request,  response))
		{
			Tournament tournament = Tournament.getTournament(essentials);
			
			for(int i = 0; i < tournament.games.size(); i++)
			{
				Game game = tournament.games.get(i);
				System.out.println((i + 1) + "," + game.blueTeam.get(0).name + "," + game.blueTeam.get(1).name + "," + game.yellowTeam.get(0).name + "," + game.yellowTeam.get(1).name);
			}
		}
	}
}
