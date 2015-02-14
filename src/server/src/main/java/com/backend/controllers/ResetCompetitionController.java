package com.backend.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.backend.models.Competition;
import com.backend.models.School;
import com.backend.models.SchoolInteger;
import com.framework.helpers.Database;
import com.framework.models.Essentials;

@WebServlet("/admin/resetCompetition")
public class ResetCompetitionController  extends HttpServlet
{
	private static final long serialVersionUID = 1787640390151356039L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try(Essentials essentials = new Essentials(new Database(Database.DatabaseType.PRODUCTION), null, null, null, null))
		{
			ArrayList<School> schools = School.getSchools(essentials);

			Competition competition = new Competition(
					null,
					new ArrayList<SchoolInteger>(),
					new ArrayList<SchoolInteger>(),
					new ArrayList<SchoolInteger>(),
					new ArrayList<SchoolInteger>(),
					new ArrayList<SchoolInteger>(),
					new ArrayList<SchoolInteger>(),
					new ArrayList<SchoolInteger>(),
					new ArrayList<SchoolInteger>());
			
			for(School school : schools)
			{
				competition.kiosk.add(new SchoolInteger(school, 0));
				competition.programming.add(new SchoolInteger(school, 0));
				competition.robotConstruction.add(new SchoolInteger(school, 0));
				competition.robotDesign.add(new SchoolInteger(school, 0));
				competition.sportsmanship.add(new SchoolInteger(school, 0));
				competition.video.add(new SchoolInteger(school, 0));
				competition.websiteDesign.add(new SchoolInteger(school, 0));
				competition.websiteJournalism.add(new SchoolInteger(school, 0));
			}
			essentials.database.dropCollection(Competition.class);

			essentials.database.save(competition);
			
			CompetitionController.showPage(essentials);
		}
	}
}
