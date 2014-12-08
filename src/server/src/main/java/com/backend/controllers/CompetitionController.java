package com.backend.controllers;

import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.bson.types.ObjectId;
import org.joda.time.Duration;

import com.backend.models.Competition;
import com.backend.models.School;
import com.backend.models.SchoolDuration;
import com.backend.models.SchoolInteger;
import com.backend.models.SkillsCompetition;
import com.framework.helpers.Helpers;
import com.framework.models.Essentials;

@WebServlet("/admin/competition")
public class CompetitionController extends HttpServlet
{
	private static final long serialVersionUID = 2943193194399939430L;

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
			
			if(action.equals("skillsCompetition"))
			{
				SkillsCompetition skillsCompetition = SkillsCompetition.get(essentials.database);
				ArrayList<SchoolInteger> pickBallsArray = new ArrayList<SchoolInteger>();
				ArrayList<SchoolDuration> twoActuatorsArray = new ArrayList<SchoolDuration>();
				ArrayList<SchoolDuration> twoTargetsArray = new ArrayList<SchoolDuration>();
				
				for(String parameter : Collections.list(request.getParameterNames()) )
				{
					final String pickballs = "pickballs_";
					final String twoTargets = "twoTargets_";
					final String twoActuators = "twoActuators_";
					
					if(parameter.startsWith(pickballs))
					{
						ObjectId id = new ObjectId(parameter.substring(pickballs.length()));
						Integer value = Helpers.getParameter(parameter, Integer.class, essentials);
						pickBallsArray.add(new SchoolInteger(essentials.database.findOne(School.class, id), value));
					}
					else if(parameter.startsWith(twoTargets))
					{
						ObjectId id = new ObjectId(parameter.substring(twoTargets.length()));
						Duration value = Helpers.stopwatchFormatter.parsePeriod(Helpers.getParameter(parameter, String.class, essentials)).toStandardDuration();
						twoTargetsArray.add(new SchoolDuration(essentials.database.findOne(School.class, id), value));
					}
					else if(parameter.startsWith(twoActuators))
					{
						ObjectId id = new ObjectId(parameter.substring(twoActuators.length()));
						Duration value = Helpers.stopwatchFormatter.parsePeriod(Helpers.getParameter(parameter, String.class, essentials)).toStandardDuration();
						twoActuatorsArray.add(new SchoolDuration(essentials.database.findOne(School.class, id), value));
					}
				}
				SkillsCompetition competition = new SkillsCompetition(skillsCompetition._id, pickBallsArray, twoTargetsArray, twoActuatorsArray);
				essentials.database.save(competition);
			}
			
			showPage(essentials);
		}
	}
	
	private void showPage(Essentials essentials) throws ServletException, IOException
	{
		ArrayList<School> schools = School.getSchools(essentials);

		Collections.sort(schools, new Comparator<School>() {
	        @Override
	        public int compare(School school1, School school2)
	        {
	        	// Do not take accents into account.
	        	Collator collator = Collator.getInstance(Locale.FRENCH);
	        	collator.setStrength(Collator.PRIMARY);
	        	return collator.compare(StringEscapeUtils.unescapeHtml(school1.name), StringEscapeUtils.unescapeHtml(school2.name));
	        }
	    });
		
		essentials.request.setAttribute("competition", Competition.get(essentials));
		essentials.request.setAttribute("skillsCompetition", SkillsCompetition.get(essentials.database));
		essentials.request.setAttribute("schools", schools);
		essentials.request.setAttribute("errorList", essentials.errorList);
		essentials.request.getRequestDispatcher("/WEB-INF/admin/competition.jsp").forward(essentials.request, essentials.response);
	}
}
