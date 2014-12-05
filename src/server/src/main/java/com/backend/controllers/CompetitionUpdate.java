package com.backend.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;

import com.backend.models.Competition;
import com.backend.models.School;
import com.framework.models.Essentials;

@WebServlet("/admin/competitionUpdate")
public class CompetitionUpdate extends HttpServlet
{
	private static final long serialVersionUID = -1616482657278664972L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try(Essentials essentials = Essentials.createEssentials(request,  response))
		{
			Competition competition = Competition.get(essentials);
			ArrayList<School> toUpdate = new ArrayList<School>();

			for (String id : request.getParameterValues("id[]"))
			{
			    toUpdate.add(essentials.database.findOne(School.class, new ObjectId(id)));
			}
			
			Competition updatedCompetition = null;

			for (String string : request.getParameterValues("arrayId")) 
			{
				switch(string)
				{
				case "kiosk":
					updatedCompetition = new Competition(competition._id, toUpdate, competition.programming, competition.robotConstruction, competition.robotDesign, competition.sportsmanship, competition.video, competition.websiteDesign, competition.websiteJournalism);
					break;
				case "programming":
					updatedCompetition = new Competition(competition._id, competition.kiosk, toUpdate, competition.robotConstruction, competition.robotDesign, competition.sportsmanship, competition.video, competition.websiteDesign, competition.websiteJournalism);
					break;
				case "robotConstruction":
					updatedCompetition = new Competition(competition._id, competition.kiosk, competition.programming, toUpdate, competition.robotDesign, competition.sportsmanship, competition.video, competition.websiteDesign, competition.websiteJournalism);
					break;
				case "robotDesign":
					updatedCompetition = new Competition(competition._id, competition.kiosk, competition.programming, competition.robotConstruction, toUpdate, competition.sportsmanship, competition.video, competition.websiteDesign, competition.websiteJournalism);
					break;
				case "sportsmanship":
					updatedCompetition = new Competition(competition._id, competition.kiosk, competition.programming, competition.robotConstruction, competition.robotDesign, toUpdate, competition.video, competition.websiteDesign, competition.websiteJournalism);
					break;
				case "video":
					updatedCompetition = new Competition(competition._id, competition.kiosk, competition.programming, competition.robotConstruction, competition.robotDesign, competition.sportsmanship, toUpdate, competition.websiteDesign, competition.websiteJournalism);
					break;
				case "websiteDesign":
					updatedCompetition = new Competition(competition._id, competition.kiosk, competition.programming, competition.robotConstruction, competition.robotDesign, competition.sportsmanship, competition.video, toUpdate, competition.websiteJournalism);
					break;
				case "websiteJournalism":
					updatedCompetition = new Competition(competition._id, competition.kiosk, competition.programming, competition.robotConstruction, competition.robotDesign, competition.sportsmanship, competition.video, competition.websiteDesign, toUpdate);
					break;
				}
			}
			essentials.database.save(updatedCompetition);
		}
	}
}
